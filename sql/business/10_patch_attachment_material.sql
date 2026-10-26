-- ----------------------------------------------------------------------------
-- 10_patch_attachment_material.sql
-- 用途：为既有库补 t_attachment 的“材料类型/关联对象键”两个字段与索引
-- 背景：小程序“上传即登记附件”需要在提交业务时把材料类型（营业执照、身份证等）
--       与关联对象（如股东姓名）落到 t_attachment，供 P16 受控预览与 PC 侧只读查看。
-- 说明：07_common.sql 已同步包含该结构（新库直接建全），本脚本仅用于已建库的升级；
--       使用 information_schema 判定 + 动态 SQL，幂等可重复执行，只动结构不碰数据。
-- 执行：mysql -uroot -p ry_vue < 10_patch_attachment_material.sql
-- ----------------------------------------------------------------------------

-- 1) material_type（材料类型码）
SET @stmt = IF(
    (SELECT COUNT(*) FROM information_schema.COLUMNS
      WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 't_attachment' AND COLUMN_NAME = 'material_type') = 0,
    'ALTER TABLE t_attachment ADD COLUMN material_type varchar(30) DEFAULT NULL COMMENT ''材料类型码（BUSINESS_LICENSE/LEGAL_PERSON_ID_CARD_FRONT 等）'' AFTER biz_id',
    'SELECT ''material_type 已存在，跳过'' AS msg');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

-- 2) ref_key（关联对象键：同一业务下多份同类材料的人员标识，如股东姓名）
SET @stmt = IF(
    (SELECT COUNT(*) FROM information_schema.COLUMNS
      WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 't_attachment' AND COLUMN_NAME = 'ref_key') = 0,
    'ALTER TABLE t_attachment ADD COLUMN ref_key varchar(64) DEFAULT NULL COMMENT ''关联对象键（同一业务下多份同类材料的人员标识，如股东姓名）'' AFTER material_type',
    'SELECT ''ref_key 已存在，跳过'' AS msg');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

-- 3) idx_material_type
SET @stmt = IF(
    (SELECT COUNT(*) FROM information_schema.STATISTICS
      WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 't_attachment' AND INDEX_NAME = 'idx_material_type') = 0,
    'ALTER TABLE t_attachment ADD KEY idx_material_type (material_type)',
    'SELECT ''idx_material_type 已存在，跳过'' AS msg');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

-- 4) idx_file_url（按上传路径回填业务归属时用，前缀索引）
SET @stmt = IF(
    (SELECT COUNT(*) FROM information_schema.STATISTICS
      WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 't_attachment' AND INDEX_NAME = 'idx_file_url') = 0,
    'ALTER TABLE t_attachment ADD KEY idx_file_url (file_url(191))',
    'SELECT ''idx_file_url 已存在，跳过'' AS msg');
PREPARE s FROM @stmt; EXECUTE s; DEALLOCATE PREPARE s;

-- 校验（应看到 material_type、ref_key 两列）
-- SHOW COLUMNS FROM t_attachment;
