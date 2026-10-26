-- ----------------------------------------------------------------------------
-- 后台账密安全加固（幂等脚本，可重复执行）
--
-- 覆盖内容：
--   1. 初始口令改为强口令（首次登录仍由 sys.account.initPasswordModify 提示修改）
--   2. 开启口令有效期 90 天（到期强制修改）
--   3. 关闭用户自助注册入口
--   4. 打开登录验证码开关
--   5. 迁移已有账号的口令计时起点，避免上线当天被有效期策略立刻锁死
--
-- 注意：
--   * sys_user.password 为 BCrypt 散列，SQL 无法安全生成，故本脚本不修改任何账号口令；
--     admin/ry 等初始账号请用「用户管理 - 重置密码」在界面上改成强口令。
--   * 明文凭据（数据库口令、令牌密钥、AES 密钥、微信凭证）已改为环境变量注入，见
--     ruoyi-admin/src/main/resources/application.yml 与 application-druid.yml 中的 ${...} 占位符。
-- ----------------------------------------------------------------------------

-- 1. 初始口令：改为一次性强口令并在首登强制修改（部署前请替换成自己的口令，
--    123456 / Administrator@2026 之类同样属于弱口令）
update sys_config set config_value = 'ChangeMe@2026', update_by = 'admin', update_time = sysdate()
where config_key = 'sys.user.initPassword';

-- 2. 口令有效期 90 天（0 表示不限制）
update sys_config set config_value = '90', update_by = 'admin', update_time = sysdate()
where config_key = 'sys.account.passwordValidateDays';

-- 3. 登录后提示修改初始密码（该配置是数字开关：1 提醒、0 关闭，写 true 会被当成非法值失效）
update sys_config set config_value = '1', update_by = 'admin', update_time = sysdate()
where config_key = 'sys.account.initPasswordModify';

-- 4. 关闭用户自助注册
update sys_config set config_value = 'false', update_by = 'admin', update_time = sysdate()
where config_key = 'sys.account.registerUser';

-- 5. 打开登录验证码（防口令爆破）
update sys_config set config_value = 'true', update_by = 'admin', update_time = sysdate()
where config_key = 'sys.account.captchaEnabled';

-- 6. 迁移：把口令更新时间过旧或为空的账号计时起点置为当前时间，避免启用有效期后立刻被要求改密
update sys_user set pwd_update_date = sysdate()
where (pwd_update_date is null or pwd_update_date < date_sub(sysdate(), interval 90 day));

-- 7. 校验结果
select config_key, config_value from sys_config
where config_key in ('sys.user.initPassword', 'sys.account.passwordValidateDays',
                     'sys.account.initPasswordModify', 'sys.account.registerUser',
                     'sys.account.captchaEnabled')
order by config_key;
