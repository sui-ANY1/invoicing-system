# 企业开票与注册平台 · 双端后端

<p>
  <img alt="Java" src="https://img.shields.io/badge/Java-21-orange">
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring%20Boot-4.1.0-brightgreen">
  <img alt="MyBatis" src="https://img.shields.io/badge/MyBatis-XML%20SQL-red">
  <img alt="MySQL" src="https://img.shields.io/badge/MySQL-8.x-4479A1">
  <img alt="Redis" src="https://img.shields.io/badge/Redis-6.x-DC382D">
  <img alt="License" src="https://img.shields.io/badge/License-MIT-yellow">
</p>

> **个人项目**——数据建模、接口设计、状态机、安全加固均由本人独立完成，基于 RuoYi-Vue 3.9.2（MIT）二次开发。
> 想解决的问题是：把「企业开票 → 税务验证 → RPA 代填 → 红冲 → 经营主体注册」这条跨端长链路做成一套可运行、也讲得清设计取舍的代码。
> 仓库内容与任何公司、雇主或商业项目无关，文中示例数据均为自造。

面向（中小）企业的**开票 + 红冲 + 税务验证 + 经营主体注册**一体化后端服务。一套服务同时承载两端：

- **微信小程序（C 端）**：微信登录、企业主体登记与实名认证、税务凭据维护、开票申请、红字（红冲）申请、经营主体注册提交、税务短信验证、待办与站内消息、票面文件下载。
- **PC 业务后台（B 端）**：开票/红字审核、工作台待办聚合、记录详情与附件、红票凭证与人工结果回填、注册信息只读查看、文件流下载。

平台自身**不做税务申报，也不重算金额**：真实开票动作由 RPA 代填电子税局完成，平台负责流程编排、状态机推进、操作留痕与结果交付；RPA 未接入时相关接口**明确返回 501**，绝不静默成功。金额一律展示税局回传的票面值，后端只做透传与格式化。

> 本仓库为**纯后端**工程。PC 前端页面不在本仓库，`sql/business/08_pc_menu.sql` 中登记的 `pc/workbench/index`、`pc/enterprise/index`、`pc/record/index`、`pc/reg/index` 等组件需放在前端工程的 `views/` 目录下。

---

## 技术亮点

先看这 10 条，都是这个仓库里真实落过代码的地方：

| # | 亮点 | 做法与取舍 | 代码位置 |
| --- | --- | --- | --- |
| 1 | **双端统一响应契约** | 小程序与 PC 共用 `{code,msg,data}`，分页固定 `{total,rows}`；**业务失败仍返回 HTTP 200**，靠 body 的 `code` 区分 401/403/500/501。放弃 RuoYi 原生 `AjaxResult`/`TableDataInfo`，避免两套结构让前端判断分叉 | `PcResult` / `PcPage` / `R` |
| 2 | **双 token 体系与隔离** | PC 走 Spring Security + HS512（30 分钟自动续期），小程序走独立签发器（默认 7 天、剩 1 天续期、Redis 前缀 `mini_tokens:` 隔离），两边 claim 结构不同、令牌不可互换 | `MiniTokenService` / `MiniAuthInterceptor` |
| 3 | **状态机 + 动作前置校验** | 审核、确认、到期、终止等动作先校验当前状态是否允许，非法流转直接 500 + 可读提示，不做「尽力而为」的静默成功 | `PcRecordService` |
| 4 | **5 桶待办的同口径 SQL** | 工作台列表与 5 个计数共用同一段 `<sql>` 条件片段，从结构上排除「列表 8 条、计数显示 7」这类口径漂移 | `PcWorkbenchMapper.xml`（11KB，全项目最复杂 SQL） |
| 5 | **统一附件模型** | 一张 `t_attachment(biz_type, biz_id, material_type, ref_key)` 同时承接开票材料、红票凭证、注册材料；材料码白名单 + 归属校验，下载统一走流式接口，不暴露磁盘路径 | `MiniAttachService` / `PcFileService` |
| 6 | **敏感字段加密与轮换** | 税务账号密码 AES-256-GCM（随机 IV + 128 位认证标签），密文带 `v2:` 版本前缀与密钥指纹，支持 `key-old` 平滑轮换；出参只回脱敏账号，永不回显密文 | `MiniCryptoUtils` |
| 7 | **启动期安全自检（fail-fast）** | 检查 4 项高危配置（默认令牌密钥、小程序密钥缺失、模拟登录开启、默认加密密钥），`APP_SECURITY_STRICT=true` 时不通过直接拒绝启动——把「带着默认密钥上线」变成启动期故障而不是线上事故 | `StartupSecurityCheck` |
| 8 | **未接入能力显式失败** | RPA 关闭时相关接口返回 501 而非伪造成功；没有 RPA 的环境也能把流程跑到该节点为止并如实展示 | `MiniRpaGate` |
| 9 | **金额口径只透传不重算** | 一律展示税局回传的票面值，后端不做换算；`null` 表示「待返回」，前端展示文案而不是 0 | `PcDict` |
| 10 | **可用的接口文档** | springdoc 踩坑修复：`@RestControllerAdvice` 会清空 200 响应 schema，用 `override-with-generic-response: false` 保住；DTO 全部带中文 `@Schema` 描述与示例 | `application.yml` / `dto/` |

其他值得一提的：双端接口按 REST 语义重构（资源化路径 + 201/501 语义）、写动作统一「留痕 + 站内信」、口令策略与 90 天有效期、操作日志排除密码/手机号/邮箱。技术栈与取舍见 [2. 技术栈](#2-技术栈) 与 [9. 开发约定](#9-开发约定)。

---

## 目录

- [技术亮点](#技术亮点)
- [1. 功能全景](#1-功能全景)
- [2. 技术栈](#2-技术栈)
- [3. 工程结构](#3-工程结构)
- [4. 数据模型](#4-数据模型)
- [5. 接口规范](#5-接口规范)
- [6. 快速开始](#6-快速开始)
- [7. 配置项与环境变量](#7-配置项与环境变量)
- [8. 安全设计](#8-安全设计)
- [9. 开发约定](#9-开发约定)
- [10. 常见问题](#10-常见问题)
- [11. 脱敏与开源说明](#11-脱敏与开源说明)
- [12. 致谢与许可](#12-致谢与许可)

---

## 1. 功能全景

### 1.1 小程序端（`/api/mini/**`）

| 模块 | 能力要点 |
| --- | --- |
| 会话登录 | `wx.login()` 的 `code` 换 `openid`；未注册自动建会员并签发小程序 token。未配置微信凭证时可开启**模拟登录**（仅限联调） |
| 企业主体 | 主体档案提交（公司/个体工商户）、列表、详情、认证状态跟踪（核验中 / 已认证 / 不通过） |
| 税务凭据 | 主体级税务账号密码维护，密码 **AES-256-GCM 加密存库**，出参只回脱敏账号，永不回显密文 |
| 开票申请 | 草稿→提交（普票/专票、企业/个人抬头、多明细行），金额支持「含税/不含税」两种口径 |
| 税务验证 | 发起验证、发送短信、重发、查询结果；一条业务可多次验证 |
| 红字申请 | 关联原蓝票发起红冲，全额/部分、原因码、红字明细 |
| 经营主体注册 | 注册收件提交（公司/个体户、股东、法人/经办人材料），历史列表、详情、附件受控下载 |
| 待办与消息 | 个人待办清单、站内消息（审核结论、状态变更推送） |
| 票面文件 | 发票详情 + 文件流式下载（文件未就绪时明确提示，不返回空文件） |

### 1.2 PC 业务后台（`/pc/**`）

| 模块 | 能力要点 |
| --- | --- |
| 开票工作台 | 5 类待办桶聚合（见下）+ 5 个同口径计数 |
| 开票企业 | 企业列表、主体详情、开票概览、认证审核（通过 / 要求补充 / 不通过）、发票列表 |
| 开票记录 | 蓝字与红字统一记录列表、记录详情（含明细、附件、审计轨迹） |
| 开票动作 | 审核通过/退回补件、补文件、删除错误附件、文件重取、确认单刷新 |
| 红字动作 | 审核、发起确认、确认成功、确认到期、终止处理、红票凭证录入 |
| 人工结果 | 无自动接口时合法人工录入办理结果（必须留痕） |
| 注册信息 | 注册提交列表与只读详情、附件受控预览（按权限放行） |
| 文件下载 | 发票文件、业务附件的流式下载，统一权限点 |

**工作台 5 类待办桶**（列表 SQL 与计数 SQL 口径完全一致）：

1. **待审核** — 开票申请、红字申请（含「确认到期」需人工介入的红字单）
2. **需补资料** — 最新审核结论为「要求补充」且尚未终结的单据
3. **待用户操作** — RPA 侧待验证、税务短信验证待完成（红字回溯到原蓝票）
4. **结果未知** — RPA 待核对、蓝票/红票处于「结果核对中」
5. **文件异常** — 文件获取失败、已开票但文件缺失

### 1.3 双端协作主流程

```text
小程序提交开票申请
      │  t_invoice_apply(0 已提交)
      ▼
PC 审核（通过 / 要求补充 / 不通过）
      │  1 待税务验证 ──► 税务短信验证 ──► 2 开票处理中
      ▼
RPA 代填电子税局 ──► 3 结果核对中 ──► 4 已开票待文件 ──► 5 交付完成
                                     └► 6 明确失败 / 7 停止处理中
                                   
红冲：t_red_apply 关联原蓝票，走「审核 → 发起确认 → 确认结果 → 红票文件 → 交付」
```

---

## 2. 技术栈

| 分类 | 选型 | 说明 |
| --- | --- | --- |
| 语言/运行时 | **Java 21** | 使用 record、switch 模式匹配等新语法 |
| 框架 | **Spring Boot 4.1.0** | Web / Security / Validation / Redis |
| 底座 | **RuoYi-Vue 3.9.2**（后端） | 保留权限、菜单、字典、日志、代码生成等基础设施 |
| 持久层 | MyBatis + PageHelper | XML 手写 SQL，复杂统计直接落 SQL |
| 数据库 | **MySQL 8.x** | 业务表 `t_*` 与 RuoYi `sys_*` 共存 |
| 连接池 | Druid | 控制台默认关闭，按需开启并限定白名单 |
| 缓存/会话 | **Redis** | PC 令牌与小程序令牌分库前缀隔离 |
| 鉴权 | JWT（HS512）+ Spring Security | 双 token 体系，见 [5.2](#52-鉴权双-token) |
| 接口文档 | springdoc-openapi | `override-with-generic-response: false`，保证响应体 schema 不丢失 |
| 文件 | POI 5.5.1 / 本地磁盘 | 统一附件表 + 业务键（`biz_type` + `biz_id`） |
| 构建 | Maven 多模块 | 无前端工程、无 Node 依赖 |

---

## 3. 工程结构

```text
.
├── pom.xml                     # 聚合 POM，统一 Java 21 / Spring Boot 4.1.0
├── ruoyi-admin/                # 启动模块：Application + 全部 application*.yml + 静态资源
├── ruoyi-framework/            # 安全框架：SecurityConfig、JWT 过滤器、全局异常处理
├── ruoyi-common/               # 通用工具：常量、Redis、Excel、脱敏、XSS、限流
├── ruoyi-system/               # ★ 全部业务代码
│   ├── src/main/java/com/ruoyi/system/
│   │   ├── mini/               # 小程序端：controller / service / dto / security / interceptor
│   │   ├── pc/                 # PC 后台：controller / service / mapper / dto / vo / support
│   │   ├── domain/             # 业务实体（Member、Enterprise、InvoiceApply、RedApply…）
│   │   ├── mapper/             # 业务 Mapper 接口
│   │   └── security/           # PasswordPolicy 口令策略
│   └── src/main/resources/mapper/
│       ├── account/ billing/ cert/ red/ reg/ audit/ common/
│       ├── mini/MiniTodoMapper.xml
│       └── pc/                 # 工作台待办聚合（本工程最复杂的 SQL）
├── ruoyi-generator/            # 代码生成器（RuoYi 自带）
├── ruoyi-quartz/               # 定时任务（RuoYi 自带）
├── sql/
│   ├── ry_20260417.sql         # RuoYi 基础表 + 菜单 + 初始数据
│   ├── quartz.sql              # 定时任务表（启用 Quartz 时需要）
│   └── business/               # ★ 业务表结构，01→10 按序执行
├── bin/                        # Windows 脚本：clean / package / run
├── ry.bat | ry.sh              # 单机服务管理脚本（启动/停止/重启/状态）
└── doc/                        # RuoYi 环境使用手册
```

模块依赖方向：`ruoyi-admin` → `ruoyi-framework` → `ruoyi-system` → `ruoyi-common`。业务代码只写在 `ruoyi-system`，`ruoyi-admin` 不出现业务逻辑。

---

## 4. 数据模型

### 4.1 表清单（`sql/business/`）

| 脚本 | 表 | 说明 |
| --- | --- | --- |
| `01_t_member.sql` | `t_member` | 小程序会员（openid/unionid/手机号/头像/状态） |
| `02_enterprise.sql` | `t_enterprise` | 开票主体档案（税号、注册地址、认证状态、审核信息） |
| | `t_enterprise_contact` | 经办人/法人身份信息、授权范围与有效期 |
| | `t_tax_credential` | 主体级税务凭据（账号脱敏展示、密码密文、密钥版本、可用状态） |
| `03_invoice.sql` | `t_invoice_apply` | 开票申请主表（申请编号唯一、抬头信息、状态、失败原因） |
| | `t_invoice_item` | 开票明细（数量、单价、含税/不含税口径、税额、票面回传金额） |
| | `t_tax_verify` | 税务验证（业务类型/业务 ID、验证方式、脱敏目标账号、状态） |
| | `t_rpa_task` | RPA 任务（阶段、重试、结果回传） |
| `04_red.sql` | `t_invoice` | 蓝/红统一发票台账（`invoice_kind`、红冲状态、文件状态） |
| | `t_red_apply` | 红字申请（红冲原因、范围、确认状态、状态机） |
| | `t_red_item` | 红字明细 |
| `05_audit.sql` | `t_audit_record` | 审核记录（结论、意见、操作人、时间） |
| | `t_manual_result` | 人工办理结果（实际结果、凭证、备注） |
| `06_reg.sql` | `t_reg_apply` / `t_reg_company` / `t_reg_shareholder` / `t_reg_sole` | 经营主体注册收件：主表、公司信息、股东、个体户 |
| `07_common.sql` | `t_message` | 站内消息 |
| | `t_attachment` | 统一附件（`biz_type` + `biz_id` + 材料类型 + 关联对象键） |
| `08_pc_menu.sql` | `sys_menu` | PC 菜单与按钮权限点（幂等，含设计稿编号 D01~D12） |
| `09_pc_role.sql` | `sys_role` / `sys_role_menu` | 内置角色 `billing_clerk`、`reg_viewer` 及授权（幂等） |
| `10_patch_attachment_material.sql` | `t_attachment` | 既有库升级补丁：材料类型/关联对象键（幂等，新库由 07 直接建全） |
| `10_security_hardening.sql` | `sys_config` / `sys_user` | 账密安全加固：初始口令、有效期、关闭自助注册、验证码（幂等） |

### 4.2 主链路

```text
t_member
   └─► t_enterprise ──┬─► t_enterprise_contact
                      └─► t_tax_credential
   └─► t_invoice_apply ──► t_invoice_item
              │                 │
              │                 └─► t_tax_verify / t_rpa_task
              └─► t_invoice（蓝票）◄── t_red_apply（红字）──► t_red_item
                      
   t_attachment(biz_type, biz_id)  ← 所有业务共用一份附件表
   t_audit_record / t_manual_result ← 所有写动作的留痕
   t_message                       ← 所有状态变更的通知
```

### 4.3 状态字典

状态码只有两处定义方：`sql/business/*.sql` 的列 `COMMENT` 与 `PcDict.java`。前端文案以 `PcDict` 返回为准。

| 字段 | 取值 |
| --- | --- |
| `t_invoice_apply.apply_status` | `0` 已提交 `1` 待税务验证 `2` 开票处理中 `3` 结果核对中 `4` 已开票待文件 `5` 交付完成 `6` 明确失败 `7` 停止处理中 |
| `t_red_apply.red_status` | `0` 已提交 `1` 待税务/对方确认 `2` 确认到期 `3` 处理中 `4` 已开红票待文件 `5` 已交付 `6` 明确失败 `7` 已停止 |
| `t_red_apply.confirm_status` | `0` 未发起 `1` 已发起待确认 `2` 已确认 `3` 已失效 |
| `t_red_apply.red_reason` | `1` 开票有误 `2` 退货折让 `3` 服务中止 `9` 其他 |
| `t_red_apply.red_range` | `1` 全额 `2` 部分 |
| `t_enterprise.auth_status` | `0` 待提交 `1` 核验中 `2` 已认证 `3` 认证不通过 |
| `t_tax_verify.verify_status` | `0` 已发起待验证 `1` 已提交待代填 `2` 验证通过 `3` 验证失败/过期 |
| `t_tax_credential.usable_status` | `0` 可用 `1` 已撤销 `2` 已过期 |
| `t_invoice.invoice_kind` | `1` 蓝票 `2` 红票 |
| `t_invoice.red_status` | `0` 未红冲 `1` 部分红冲 `2` 全额红冲 |
| `t_invoice.file_status` | `0` 待文件 `1` 已获取 `2` 获取失败 |
| `t_audit_record.audit_result` | `1` 通过 `2` 要求补充 `3` 不通过 |
| `t_manual_result.actual_result` | `1` 已交付 `2` 办理失败 `9` 其他 |

---

## 5. 接口规范

### 5.1 统一响应

两端字段完全一致（`code` / `msg` / `data`），**不使用** RuoYi 原生 `AjaxResult` / `TableDataInfo`：

```json
{ "code": 200, "msg": "操作成功", "data": { } }
```

分页数据的 `data` 固定为 `{ "total": 100, "rows": [ ] }`（PC 用 `PcPage<T>`，小程序用 `R<T>` 的同类结构）。

| 实现 | 位置 | 用于 |
| --- | --- | --- |
| `PcResult<T>` / `PcPage<T>` | `ruoyi-system/.../pc/support/` | PC 后台全部接口 |
| `R<T>` | `ruoyi-system/.../mini/dto/` | 小程序全部接口 |

**关键约定：未登录 / 无权限的 HTTP 状态码仍是 200**，客户端必须读响应体里的 `code`：

| `code` | 含义 |
| --- | --- |
| `200` | 成功 |
| `401` | 未登录或令牌失效（小程序拦截器同样返回 200 + `code=401`） |
| `403` | 已登录但无权限点 |
| `500` | 业务异常（`msg` 为可直接展示的提示语） |
| `501` | 依赖能力未接入（如 RPA 关闭时的开票动作） |

### 5.2 鉴权（双 token）

| 端 | 签发 | 密钥配置 | 有效期 | Redis 前缀 |
| --- | --- | --- | --- | --- |
| PC 后台 | RuoYi `TokenService`，Spring Security 过滤器链 | `token.secret` ← `TOKEN_SECRET` | `token.expireTime`（默认 30 分钟，自动续期） | `login_tokens:` |
| 小程序 | `MiniTokenService`，`MiniAuthInterceptor` | `mini.token.secret` ← `MINI_TOKEN_SECRET`（未配则回退后台密钥） | `mini.token.expire-days`（默认 7 天，剩 1 天自动续期） | `mini_tokens:` |

两端 claim 结构不同（PC 放 `login_user` 会话串，小程序放 `mini_login_user_key`），**令牌不可互换**。请求头统一为 `Authorization: Bearer <token>`（`Constants.TOKEN_PREFIX`）。

小程序免登录白名单仅 3 条：

```text
POST /api/mini/sessions                      # 登录
POST /api/mini/registration-submissions      # 注册收件（允许先提交）
POST /api/mini/files                         # 附件上传（受限白名单）
```

### 5.3 小程序接口（共 24 个）

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/mini/sessions` | 微信登录 / 自动注册，返回 token |
| POST | `/api/mini/files` | 附件上传（multipart，白名单校验类型与大小） |
| GET | `/api/mini/enterprises` | 我的主体列表（分页） |
| POST | `/api/mini/enterprises` | 提交主体档案（201） |
| GET | `/api/mini/enterprises/detail` | 主体详情（`?enterpriseId=`） |
| PUT | `/api/mini/enterprises/tax-credential` | 维护税务凭据（入参加密落库） |
| POST | `/api/mini/invoice-applications` | 提交开票申请（201） |
| GET | `/api/mini/invoice-applications` | 开票中心列表（分页） |
| GET | `/api/mini/invoice-applications/detail` | 开票申请详情（`?applyId=`） |
| POST | `/api/mini/red-applications` | 提交红字申请（201） |
| GET | `/api/mini/red-applications` | 红字申请列表 |
| GET | `/api/mini/red-applications/detail` | 红字申请详情（`?redApplyId=`） |
| POST | `/api/mini/tax-verifications` | 发起税务验证 |
| POST | `/api/mini/tax-verifications/code` | 提交验证码 |
| POST | `/api/mini/tax-verifications/resends` | 重发验证码 |
| GET | `/api/mini/tax-verifications/detail` | 验证结果（`?verifyId=`） |
| POST | `/api/mini/registration-submissions` | 提交经营主体注册（201） |
| GET | `/api/mini/registration-submissions` | 注册提交历史 |
| GET | `/api/mini/registration-submissions/detail` | 注册详情（`?submissionId=`） |
| GET | `/api/mini/registration-submissions/attachments/file` | 注册附件下载（流式） |
| GET | `/api/mini/invoices/detail` | 发票详情（`?invoiceId=`） |
| GET | `/api/mini/invoices/file` | 票面文件下载（流式） |
| GET | `/api/mini/todos` | 我的待办 |
| GET | `/api/mini/messages` | 站内消息 |

### 5.4 PC 接口（共 21 个）

| 方法 | 路径 | 权限点 | 说明 |
| --- | --- | --- | --- |
| GET | `/pc/workbench/todos/count` | `pc:workbench:list` | 5 类待办计数 |
| GET | `/pc/workbench/todos` | `pc:workbench:list` | 待办列表（按桶筛选） |
| GET | `/pc/enterprises` | `pc:enterprise:list` | 企业列表 |
| GET | `/pc/enterprises/detail` | `pc:enterprise:query` | 主体详情 |
| GET | `/pc/enterprises/invoice-summary` | `pc:enterprise:query` | 开票概览 |
| GET | `/pc/enterprises/invoices` | `pc:record:list` | 该主体下的发票列表 |
| POST | `/pc/enterprises/audits` | `pc:enterprise:audit` | 认证审核 |
| GET | `/pc/records` | `pc:record:list` | 开票/红字统一记录 |
| GET | `/pc/records/detail` | `pc:record:query` | 记录详情（`?bizType=`） |
| GET | `/pc/records/attachments` | `pc:record:query` | 记录附件列表 |
| POST | `/pc/records/audits` | `pc:invoice:audit` | 开票审核（通过/退回补件） |
| POST | `/pc/records/file-fetches` | `pc:invoice:handle` | 文件重取 |
| POST | `/pc/records/red-handlings` | `pc:red:handle` | 红字动作（审核/确认/到期/终止） |
| POST | `/pc/records/red-vouchers` | `pc:red:handle` | 红票凭证录入 |
| POST | `/pc/records/confirm-refreshes` | `pc:red:handle` | 确认单刷新 |
| POST | `/pc/records/manual-results` | `pc:manual:save` | 人工结果回填 |
| GET | `/pc/registrations` | `pc:reg:list` | 注册提交列表 |
| GET | `/pc/registrations/detail` | `pc:reg:query` | 注册详情（只读） |
| GET | `/pc/registrations/attachments/file` | `pc:reg:preview` | 注册附件受控预览 |
| GET | `/pc/invoices/file` | `pc:file:download` | 发票文件下载 |
| GET | `/pc/attachments/file` | `pc:file:download` | 业务附件下载 |

### 5.5 权限点与内置角色

| 权限点 | 类型 | 说明 |
| --- | --- | --- |
| `pc:workbench:list` | 菜单 | 开票工作台（D01） |
| `pc:enterprise:list` / `:query` / `:audit` | 菜单 / 按钮 | 企业列表、详情、认证审核（D02） |
| `pc:file:download` | 按钮 | 发票文件下载（D03） |
| `pc:record:list` / `:query` | 菜单 / 按钮 | 记录列表与详情（D04/D05） |
| `pc:invoice:audit` / `:handle` | 按钮 | 开票审核、补文件/删件 |
| `pc:red:handle` | 按钮 | 红字全套处理 |
| `pc:manual:save` | 按钮 | 人工结果回填 |
| `pc:reg:list` / `:query` / `:preview` | 菜单 / 按钮 | 注册信息只读（D11/D12） |

内置角色：`billing_clerk`（开票业务员，开票业务目录 + 全部按钮）、`reg_viewer`（注册只读员，仅注册信息目录，无任何写权限）。

---

## 6. 快速开始

### 6.1 环境要求

| 依赖 | 版本 | 备注 |
| --- | --- | --- |
| JDK | **21** | 编译目标即为 21，低版本无法构建 |
| Maven | 3.9+ | 无 wrapper，需本机安装 |
| MySQL | 8.0+ | 建议 `utf8mb4` |
| Redis | 6.0+ | 令牌与缓存强依赖，未启动则登录失败 |

### 6.2 初始化数据库

```bash
# 1) 建库
mysql -uroot -p -e "CREATE DATABASE invoicing DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_general_ci;"

# 2) RuoYi 基础表与初始数据
mysql -uroot -p invoicing < sql/ry_20260417.sql

# 3) 定时任务表（不使用 Quartz 可跳过）
mysql -uroot -p invoicing < sql/quartz.sql

# 4) 业务表：按文件名顺序执行 01 → 10
mysql -uroot -p invoicing < sql/business/01_t_member.sql
mysql -uroot -p invoicing < sql/business/02_enterprise.sql
mysql -uroot -p invoicing < sql/business/03_invoice.sql
mysql -uroot -p invoicing < sql/business/04_red.sql
mysql -uroot -p invoicing < sql/business/05_audit.sql
mysql -uroot -p invoicing < sql/business/06_reg.sql
mysql -uroot -p invoicing < sql/business/07_common.sql
mysql -uroot -p invoicing < sql/business/08_pc_menu.sql
mysql -uroot -p invoicing < sql/business/09_pc_role.sql
mysql -uroot -p invoicing < sql/business/10_security_hardening.sql
```

说明：

- 新库由 `07_common.sql` 直接建全附件结构，`10_patch_attachment_material.sql` 只用于**升级既有库**，新库无需执行。
- `08` / `09` / `10_*` 三个脚本均为**幂等**，可重复执行；`08` 会先按 `perms like 'pc:%'` 清理旧菜单再重建。
- `10_security_hardening.sql` 会把 `sys.user.initPassword` 改成一次性强口令并强制首登改密；上线前请把该值改成自己的口令。脚本不修改任何已有账号的 BCrypt 口令，`admin` 等初始账号请在「用户管理 → 重置密码」里改成强口令。

### 6.3 最小配置（启动前必做）

服务**不会**带着内置密钥启动。至少注入以下环境变量：

```bash
# 数据库
export DB_HOST=127.0.0.1
export DB_PORT=3306
export DB_NAME=invoicing
export DB_USERNAME=root
export DB_PASSWORD='你的数据库口令'

# 令牌密钥（必填，≥32 位随机串；小程序侧建议另配独立密钥）
export TOKEN_SECRET='用 openssl rand -hex 32 生成'
export MINI_TOKEN_SECRET='另一串随机密钥'

# 敏感字段加密密钥（税务账号密码）
export MINI_CRYPTO_KEY='≥32 位随机串'
```

Windows PowerShell 用 `$env:TOKEN_SECRET = "..."` 设置。

### 6.4 构建与运行

```bash
# 打包（跳过测试）
mvn clean package -DskipTests

# 方式一：直接跑 jar
java -jar ruoyi-admin/target/ruoyi-admin.jar

# 方式二：IDEA 直接运行 com.ruoyi.RuoYiApplication（spring.profiles.active=druid）

# 方式三：Windows 服务脚本（由 jar 所在目录启动）
bin/run.bat          # 或 ry.bat 走菜单
```

指定外部配置与上传目录：

```bash
java -jar ruoyi-admin/target/ruoyi-admin.jar \
  --spring.config.additional-location=/etc/invoicing/application.yml \
  --ruoyi.profile=/data/invoicing/uploadPath
```

### 6.5 启动自检

启动日志中应出现：

```text
[启动自检] 安全配置检查通过
```

若出现 `[启动自检] ...` 告警，说明仍有内置密钥、模拟登录或默认加密密钥未替换。设置 `APP_SECURITY_STRICT=true` 后，任何一项不合规都会**直接拒绝启动**（`IllegalStateException: 生产安全检查未通过，拒绝启动`），适合作为生产环境的守门员。

访问验证：

| 地址 | 说明 |
| --- | --- |
| `http://localhost:8080/v3/api-docs` | OpenAPI 规格（可导入 Apifox / Postman） |
| `http://localhost:8080/swagger-ui.html` | Swagger UI |
| `http://localhost:8080/druid/` | Druid 监控台（默认关闭，需 `DRUID_CONSOLE_ENABLED=true`） |

---

## 7. 配置项与环境变量

### 7.1 数据源与基础设施

| 环境变量 | 默认 | 说明 |
| --- | --- | --- |
| `DB_HOST` / `DB_PORT` / `DB_NAME` | `localhost` / `3306` / `ry-vue` | 数据库地址，**仓库内不含任何真实地址** |
| `DB_USERNAME` / `DB_PASSWORD` | `root` / 空 | 数据库账号口令 |
| Redis | `localhost:6379`、db 0、无口令 | 见 `application.yml` 的 `spring.data.redis` |
| `DRUID_CONSOLE_ENABLED` | `false` | Druid 监控台总开关 |
| `DRUID_CONSOLE_ALLOW` | `127.0.0.1` | 监控台 IP 白名单，留空表示不限制（生产禁止） |
| `DRUID_CONSOLE_USERNAME` / `DRUID_CONSOLE_PASSWORD` | `ruoyi` / 空 | 监控台账号口令，必须覆盖 |
| `ruoyi.profile` | `D:/ruoyi/uploadPath` | 上传文件根目录，**部署时必须改成服务器路径** |

### 7.2 安全与令牌

| 环境变量 | 对应配置 | 默认 | 说明 |
| --- | --- | --- | --- |
| `TOKEN_SECRET` | `token.secret` | **空（必填）** | PC 令牌签名密钥，未设置直接启动失败 |
| `MINI_TOKEN_SECRET` | `mini.token.secret` | 空 | 小程序令牌独立密钥，留空回退 `TOKEN_SECRET`（不推荐） |
| `MINI_CRYPTO_KEY` | `mini.crypto.key` | 空 | 税务凭据 AES-256-GCM 密钥，空值时启动告警 |
| `MINI_CRYPTO_KEY_OLD` | `mini.crypto.key-old` | 空 | 历史密钥（逗号分隔），仅用于解密旧密文 |
| `APP_SECURITY_STRICT` | `app.security.strict` | `false` | `true` 时启动自检不通过即拒绝启动 |

### 7.3 业务开关

| 环境变量 | 默认 | 说明 |
| --- | --- | --- |
| `MINI_WX_APP_ID` / `MINI_WX_APP_SECRET` | 空 | 微信小程序凭证 |
| `MINI_WX_MOCK_ENABLED` | `true` | 未配凭证时允许模拟登录，**生产必须为 `false`** |
| `MINI_RPA_ENABLED` | `false` | RPA 开关；关闭时依赖 RPA 的接口返回 501，不静默成功 |
| `mini.reg.require-materials` | `false` | 注册收件是否强制上传材料 |

其余配置（端口、线程池、日志级别、分页插件、XSS、防盗链、springdoc 分组等）见 `ruoyi-admin/src/main/resources/application.yml`，均有中文注释。

---

## 8. 安全设计

| 层面 | 措施 |
| --- | --- |
| 密钥管理 | 数据库口令、令牌密钥、AES 密钥、微信凭证**全部**改为环境变量注入，仓库内只保留空占位符 |
| 启动自检 | `StartupSecurityCheck` 检查 4 项高危配置（内置令牌密钥、小程序密钥缺失、模拟登录开启、默认加密密钥），strict 模式直接阻断启动 |
| 口令策略 | `PasswordPolicy` 接入新增用户 / 修改口令 / 自助注册，强制长度与字符组合 |
| 口令有效期 | `sys.account.passwordValidateDays=90`，`initPasswordModify=1` 首登强制改密 |
| 敏感字段加密 | 税务账号密码 AES-256-GCM（随机 IV + 认证标签），密文带 `v2:` 版本前缀，`keyVersion` 记录密钥指纹支持平滑轮换 |
| 数据脱敏 | 手机号/邮箱在列表与导出中脱敏；税务账号只回脱敏值；操作日志排除 `password`、手机号、邮箱 |
| 附件访问 | 附件按业务键归属校验，下载走统一权限点，不暴露磁盘路径 |
| 越权防护 | 小程序侧所有查询强制带 `memberId` 条件；PC 侧按权限点 + 主体归属校验 |
| 其他 | XSS 过滤、验证码开关、登录失败锁定（5 次 / 10 分钟）、Druid 控制台默认关闭 |

生产上线检查清单：

- [ ] `TOKEN_SECRET`、`MINI_TOKEN_SECRET`、`MINI_CRYPTO_KEY` 均为随机强密钥且互不相同
- [ ] `MINI_WX_MOCK_ENABLED=false`，微信凭证已配置
- [ ] `APP_SECURITY_STRICT=true`，启动日志出现「安全配置检查通过」
- [ ] `ruoyi.profile` 指向独立数据盘，且不在 Web 根目录下
- [ ] `DRUID_CONSOLE_ENABLED=false`（或已配置强口令 + IP 白名单）
- [ ] `sys.user.initPassword` 已改成本方口令，`admin` 口令已重置
- [ ] 反向代理启用 HTTPS，`server.tomcat` 线程数与 `accept-count` 按并发调整

---

## 9. 开发约定

1. **响应结构**：新接口一律用 `PcResult<T>` / `R<T>`，分页用 `PcPage<T>`。不要引入 `AjaxResult` / `TableDataInfo`，两套结构混用会让前端判断逻辑分叉。
2. **状态码**：只在 `sql/business/*.sql` 的列注释与 `PcDict.java` 两处定义，新增状态必须同步两边。
3. **金额**：只展示税局回传的票面值，后端不做换算；`null` 表示「待返回」，前端展示「待返回」而不是 0。
4. **写动作必须留痕**：任何状态变更都要落 `t_audit_record`（`writeAudit`）并给用户发 `t_message`（`sendMessage`）。
5. **动作前置校验**：状态机动作（审核、确认、终止）先校验当前状态是否允许，非法流转返回 500 + 可读提示，不做「尽力而为」。
6. **SQL 归属**：复杂查询（尤其工作台 5 桶聚合与计数）写在 `mapper/pc/*.xml`，列表与计数复用同一段 `where` 片段，避免口径漂移。
7. **敏感数据**：日志、`toString`、接口出参都不得出现明文口令、完整证件号、完整手机号。
8. **提交信息**：遵循 `feat(scope):` / `fix(scope):` / `chore(config):` / `docs:` 风格，`scope` 取 `mini` / `pc` / `system` / `config`。

---

## 10. 常见问题

**Q：启动报 `token.secret 未配置，无法签发令牌`？**
A：没有设置 `TOKEN_SECRET`。本仓库刻意不提供默认令牌密钥，必须显式注入。

**Q：小程序登录一直失败？**
A：确认 Redis 已启动（登录态存 Redis），以及 `MINI_WX_APP_ID` / `MINI_WX_APP_SECRET` 是否配置。未配置凭证时只能靠 `MINI_WX_MOCK_ENABLED=true` 的模拟登录。

**Q：接口返回 HTTP 200 但业务失败？**
A：项目约定如此，请读响应体 `code`（401 未登录、403 无权限、500 业务异常、501 能力未接入）。

**Q：工作台待办数与列表条数对不上？**
A：说明列表 SQL 与计数 SQL 的口径被改歪了。两者必须共用 `PcWorkbenchMapper.xml` 里的同一段条件，新增桶时同时改 5 个计数。

**Q：开票/红字接口返回 501？**
A：`MINI_RPA_ENABLED=false`，RPA 未接入。这是设计行为——平台不会伪造成功结果，可在没有 RPA 的环境里完整验证流程直到该节点。

**Q：上传文件后访问 404？**
A：检查 `ruoyi.profile` 目录是否存在且进程有写权限；下载只走 `/pc/invoices/file`、`/pc/attachments/file`、`/api/mini/invoices/file` 等流式接口，不直接暴露静态路径。

---

## 11. 脱敏与开源说明

本仓库在上传前做过一次完整脱敏：

- 移除了数据库真实地址、库名、账号口令的**全部默认值**，改为 `DB_HOST` / `DB_PORT` / `DB_NAME` / `DB_USERNAME` / `DB_PASSWORD` 占位；
- 移除了后台令牌密钥、小程序令牌密钥、AES 加密密钥的内置默认值；
- Druid 监控台密码不再有默认值，且默认关闭；
- SQL 脚本中的一次性初始口令改为中性占位值（部署前请自行修改）；
- 代码注释与文档中的内网地址、VPN/堡垒机信息、第三方平台 AppSecret 一概未纳入本仓库。

仓库内的 `localhost`、`13800000000`、`320501199001011234`、`admin123` 等均为示例/占位数据。

---

## 12. 致谢与许可

本项目基于 [RuoYi-Vue](https://gitee.com/y_project/RuoYi-Vue)（v3.9.2）二次开发，沿用其权限、菜单、字典、日志等基础设施，并按业务重构了响应结构、鉴权体系与数据模型。

遵循 **MIT License**，详见 [LICENSE](LICENSE)。上游版权：Copyright (c) 2018 RuoYi。

---

**作者**：[@sui-ANY1](https://github.com/sui-ANY1) · 欢迎 issue 交流实现细节。
