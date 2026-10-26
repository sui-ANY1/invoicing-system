-- =====================================================================
-- PC 业务后台 角色与授权（阶段4 · 任务23）
-- 依赖：先执行 08_pc_menu.sql
-- 说明：本脚本幂等，可重复执行：
--   1) 角色不存在才插入，已存在则只更新属性 —— 保持 role_id 稳定，
--      重复执行不会让 sys_user_role 里已分配的绑定失效；
--   2) 授权部分先删本角色旧绑定再重建。
--   开票业务员 billing_clerk = 整个"开票业务"目录（工作台/企业/记录及全部按钮）
--   注册只读员 reg_viewer   = 仅"注册信息"目录（列表/详情/附件预览，无任何写权限）
-- =====================================================================

-- ---------- 角色 1：开票业务员 ----------
insert into sys_role (role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
select '开票业务员', 'billing_clerk', 10, 1, 1, 1, '0', '0', 'admin', sysdate(), 'PC开票业务处理：工作台/企业认证审核/开票与红字处理'
from dual where not exists (select 1 from sys_role where role_key = 'billing_clerk');

update sys_role set role_name = '开票业务员', role_sort = 10, data_scope = 1, menu_check_strictly = 1,
       dept_check_strictly = 1, status = '0', del_flag = '0',
       remark = 'PC开票业务处理：工作台/企业认证审核/开票与红字处理'
where role_key = 'billing_clerk';

-- ---------- 角色 2：注册只读员 ----------
insert into sys_role (role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
select '注册只读员', 'reg_viewer', 11, 1, 1, 1, '0', '0', 'admin', sysdate(), 'PC注册信息只读：仅列表/详情/受权附件预览'
from dual where not exists (select 1 from sys_role where role_key = 'reg_viewer');

update sys_role set role_name = '注册只读员', role_sort = 11, data_scope = 1, menu_check_strictly = 1,
       dept_check_strictly = 1, status = '0', del_flag = '0',
       remark = 'PC注册信息只读：仅列表/详情/受权附件预览'
where role_key = 'reg_viewer';

-- ---------- 重新授权：先清旧绑定 ----------
delete rm from sys_role_menu rm
join sys_role r on r.role_id = rm.role_id
where r.role_key in ('billing_clerk', 'reg_viewer');

-- 开票业务员：目录本身 + 二级菜单 + 三级按钮（固定三层，不用递归）
insert into sys_role_menu (role_id, menu_id)
select r.role_id, m.menu_id
from sys_role r
join sys_menu m on (
        (m.menu_name = '开票业务' and m.parent_id = 0)
     or m.parent_id in (select menu_id from sys_menu where menu_name = '开票业务' and parent_id = 0)
     or m.parent_id in (
            select menu_id from sys_menu
            where parent_id in (select menu_id from sys_menu where menu_name = '开票业务' and parent_id = 0)
        )
)
where r.role_key = 'billing_clerk';

-- 注册只读员：目录本身 + 二级菜单 + 三级按钮
insert into sys_role_menu (role_id, menu_id)
select r.role_id, m.menu_id
from sys_role r
join sys_menu m on (
        (m.menu_name = '注册信息' and m.parent_id = 0)
     or m.parent_id in (select menu_id from sys_menu where menu_name = '注册信息' and parent_id = 0)
     or m.parent_id in (
            select menu_id from sys_menu
            where parent_id in (select menu_id from sys_menu where menu_name = '注册信息' and parent_id = 0)
        )
)
where r.role_key = 'reg_viewer';

-- =====================================================================
-- 验证 SQL（执行后可自查，应分别返回 16、12、4）
--   select count(*) from sys_menu where perms like 'pc:%' or (menu_name in ('开票业务','注册信息') and parent_id=0);  -- 16
--   select count(*) from sys_role_menu rm join sys_role r on rm.role_id=r.role_id where r.role_key='billing_clerk';  -- 12
--   select count(*) from sys_role_menu rm join sys_role r on rm.role_id=r.role_id where r.role_key='reg_viewer';    -- 4
--
-- 把角色分给测试账号：系统管理-用户-编辑对应用户-勾选角色（或后续用 SQL 关联 sys_user_role）。
-- admin 超管默认拥有全部权限，无需授权。
-- =====================================================================
