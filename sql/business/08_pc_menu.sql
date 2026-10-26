-- =====================================================================
-- PC 业务后台 菜单与按钮权限（阶段4 · 任务23）
-- 依赖表：sys_menu（RuoYi 自带）
-- 说明：
--   1. 本脚本可重复执行：先清理本脚本创建的 PC 菜单及其角色绑定，再重建（幂等）。
--      注意：清理会解除 PC 菜单与角色的绑定，执行本脚本后需再执行 09_pc_role.sql 重新授权。
--   2. component 为前端 vue 组件约定路径（前端由别人对接，可对齐后微调，不影响后端接口与权限）。
--   3. 路径前缀 /pc/** 的业务接口默认需要登录（SecurityConfig 已配置 anyRequest().authenticated()），
--      按钮级权限通过 @PreAuthorize("@ss.hasPermi('这里的 perms')") 控制。
--
-- 菜单结构：
--   开票业务(M)
--     开票工作台(C)             pc:workbench:list
--     开票企业(C)               pc:enterprise:list
--       企业信息查看(F)          pc:enterprise:query
--       企业认证审核(F)          pc:enterprise:audit
--       发票文件下载(F)          pc:file:download
--     开票记录(C)               pc:record:list
--       记录详情查看(F)          pc:record:query
--       开票审核(F)             pc:invoice:audit
--       开票处理(F)             pc:invoice:handle
--       红字处理(F)             pc:red:handle
--       人工结果回填(F)          pc:manual:save
--   注册信息(M)
--     注册信息(C)               pc:reg:list
--       注册详情查看(F)          pc:reg:query
--       注册附件预览(F)          pc:reg:preview
-- =====================================================================

-- ========== 幂等清理：删除本脚本创建过的 PC 菜单与角色绑定 ==========
delete from sys_role_menu where menu_id in (
  select menu_id from sys_menu where perms like 'pc:%'
);
delete from sys_role_menu where menu_id in (
  select menu_id from sys_menu where parent_id = 0 and menu_name in ('开票业务', '注册信息')
);
delete from sys_menu where perms like 'pc:%';
delete from sys_menu where parent_id = 0 and menu_name in ('开票业务', '注册信息');

-- ========== 一级目录：开票业务 ==========
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('开票业务', 0, 10, 'biz', null, 1, 0, 'M', '0', '0', '', 'form', 'admin', sysdate(), '', null, 'PC开票业务目录');
SELECT @bizId := LAST_INSERT_ID();

-- 开票工作台（D01）
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('开票工作台', @bizId, 1, 'workbench', 'pc/workbench/index', 1, 0, 'C', '0', '0', 'pc:workbench:list', 'dashboard', 'admin', sysdate(), '', null, 'D01 开票工作台');

-- 开票企业（D02）
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('开票企业', @bizId, 2, 'enterprise', 'pc/enterprise/index', 1, 0, 'C', '0', '0', 'pc:enterprise:list', 'peoples', 'admin', sysdate(), '', null, 'D02 开票企业');
SELECT @entId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('企业信息查看', @entId, 1, '#', '', 1, 0, 'F', '0', '0', 'pc:enterprise:query', '#', 'admin', sysdate(), '', null, 'D02-V1 企业信息弹窗');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('企业认证审核', @entId, 2, '#', '', 1, 0, 'F', '0', '0', 'pc:enterprise:audit', '#', 'admin', sysdate(), '', null, '通过/要求补充/不通过');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('发票文件下载', @entId, 3, '#', '', 1, 0, 'F', '0', '0', 'pc:file:download', '#', 'admin', sysdate(), '', null, 'D03 行内下载发票文件');

-- 开票记录（D04/D05）
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('开票记录', @bizId, 3, 'record', 'pc/record/index', 1, 0, 'C', '0', '0', 'pc:record:list', 'list', 'admin', sysdate(), '', null, 'D04/D05 开票与红字统一记录');
SELECT @recId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('记录详情查看', @recId, 1, '#', '', 1, 0, 'F', '0', '0', 'pc:record:query', '#', 'admin', sysdate(), '', null, 'D05 开票/红字详情');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('开票审核', @recId, 2, '#', '', 1, 0, 'F', '0', '0', 'pc:invoice:audit', '#', 'admin', sysdate(), '', null, '开票申请通过/退回补件');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('开票处理', @recId, 3, '#', '', 1, 0, 'F', '0', '0', 'pc:invoice:handle', '#', 'admin', sysdate(), '', null, '补文件/删件（D05-S2 实际能力）');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('红字处理', @recId, 4, '#', '', 1, 0, 'F', '0', '0', 'pc:red:handle', '#', 'admin', sysdate(), '', null, '红字审核/确认/补文件');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('人工结果回填', @recId, 5, '#', '', 1, 0, 'F', '0', '0', 'pc:manual:save', '#', 'admin', sysdate(), '', null, '无自动接口时合法人工录入');

-- ========== 一级目录：注册信息（只读） ==========
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('注册信息', 0, 11, 'regview', null, 1, 0, 'M', '0', '0', '', 'documentation', 'admin', sysdate(), '', null, 'PC注册只读目录');
SELECT @regDirId := LAST_INSERT_ID();

-- 注册信息列表（D11/D12）
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('注册信息', @regDirId, 1, 'reg', 'pc/reg/index', 1, 0, 'C', '0', '0', 'pc:reg:list', 'documentation', 'admin', sysdate(), '', null, 'D11/D12 注册只读列表详情');
SELECT @regMenuId := LAST_INSERT_ID();

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('注册详情查看', @regMenuId, 1, '#', '', 1, 0, 'F', '0', '0', 'pc:reg:query', '#', 'admin', sysdate(), '', null, '只读详情');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('注册附件预览', @regMenuId, 2, '#', '', 1, 0, 'F', '0', '0', 'pc:reg:preview', '#', 'admin', sysdate(), '', null, '受权附件预览');

-- =====================================================================
-- 角色建议（在 RuoYi 后台界面 系统管理-角色 中创建，勾选菜单即可）：
--   开票业务员  role_key=billing_clerk  勾选整个"开票业务"目录
--   注册只读员  role_key=reg_viewer    只勾选"注册信息"目录（列表/详情/预览，无任何写权限）
--   admin 超管默认拥有全部权限，无需授权
-- =====================================================================
