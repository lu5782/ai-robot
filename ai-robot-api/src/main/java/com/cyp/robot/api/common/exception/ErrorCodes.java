package com.cyp.robot.api.common.exception;

public enum ErrorCodes {


    /**
     * 登录忘记密码2-50
     */
    CAPTCHA_VERIFY_CODE_ERROR(2, "图片验证码错误"),
    MOBILE_VERIFY_CODE_ERROR(3, "手机验证码错误"),
    AUTH_SEND_SMS_ERROR(4, "发送验证码失败"),
    AUTH_SEND_SMS_TOO_OFTEN(5, "发送验证码--一分钟内只许发送一次"),
    AUTH_SEND_SMS_MOBILE_PARAM_REQUIRED(6, "发送验证码--参数 mobile 是必须的"),
    AUTH_SEND_SMS_TYPE_PARAM_REQUIRED(7, "发送验证码--参数 type 是必须的"),
    AUTH_SEND_SMS_TYPE_PARAM_VALUE_ERROR(8, "发送验证码--参数 type 的值不支持"),
    AUTH_SEND_SMS_THIRD_PARTY_ERROR(9, "发送验证码--调用第三方短信通道失败"),
    AUTH_LOGIN_ERROR(10, "登录失败"),
    AUTH_LOGIN_MOBILE_PARAM_REQUIRED(11, "登录--参数 mobile 是必须的"),
    AUTH_LOGIN_CAPTCHAVERIFYCODE_PARAM_REQUIRED(12, "登录--参数 CaptchaVerifyCode 是必须的"),
    AUTH_LOGIN_PASSWORD_OR_MOBILEVERIFYCODE_PARAM_REQUIRED(13, "登录--参数 password 或 mobileVerifyCode 必须有其一"),
    AUTH_LOGIN_MOBILE_OR_PASSWORD_ERROR(14, "手机号码与密码不匹配"),
    AUTH_FORGOT_PASSWORD_ERROR(15, "忘记密码修改失败"),
    AUTH_FORGOT_PASSWORD_MOBILE_PARAM_REQUIRED(16, "忘记密码--参数 mobile 是必须的"),
    AUTH_FORGOT_PASSWORD_MOBILEVERIFYCODE_PARAM_REQUIRED(17, "忘记密码--参数 mobileVerifyCode 是必须的"),
    AUTH_FORGOT_PASSWORD_PASSWORD_PARAM_REQUIRED(18, "忘记密码--参数 password 是必须的"),

    /**
     * 员工管理-员工列表51-100
     */
    STAFF_LIST_ERROR(51, "查询员工列表失败"),
    STAFF_INFO_BY_STAFF_ID_ERROR(52, "查看个人资料失败"),
    STAFF_INFO_ERROR(53, "查询员工详情失败"),
    PERSONAL_UPLOAD_STAFF_PHOTO_ERROR(54, "上传个人头像失败"),
    PERSONAL_UPLOAD_STAFF_PHOTO_HEADIMAGE_PARAM_REQUIRED(55, "上传个人头像--参数 headimage 是必须的"),
    STAFF_LIST_ADD_ERROR(56, "新增员工失败"),
    STAFF_LIST_EDIT_ERROR(57, "编辑员工失败"),
    STAFF_LIST_EDIT_MOBILE_ERROR(58, "编辑员工失败，启用手机号码唯一"),
    STAFF_LIST_EDIT_STATUS_ERROR(59, "员工停用启用失败"),
    STAFF_LIST_EDIT_STATUS_MOBILE_ERROR(60, "员工启用失败，启用手机号码唯一"),
    STAFF_LIST_DEL_ERROR(61, "员工删除失败"),
    STAFF_LIST_ROLE_ERROR(62, "查看个人菜单权限失败"),
    SUPER_STAFF_INFO_LIST_ERROR(63, "查询超级管理员异常"),
    SUPER_STAFF_INFO_ADD_ERROR(64, "添加超级管理员异常"),
    SUPER_STAFF_INFO_DEL_ERROR(65, "删除超级管理员异常"),
    SUPER_STAFF_INFO_TURNON_ERROR(66, "启用超级管理员异常"),
    SUPER_STAFF_INFO_MOBILE_EXIST(67, "账号已存在"),
    PERSONAL_GET_STAFF_NAME_FILTER_ERROR(68, "获取用户名过滤器失败"),
    STAFF_LIST_ADD_REJECT(69, "无权限"),
    STAFF_PASSWORD_ERROR(70, "旧密码不正确"),

    /**
     * 员工管理-部门管理101-150
     */
    STAFF_DEPT_ERROR(101, "查询部门失败"),
    STAFF_DEPT_POSITION_ERROR(102, "查询部门职位失败"),
    STAFF_DEPT_BY_ID_ERROR(103, "获取本部门上级部门职位失败"),
    STAFF_DEPT_ADD_ERROR(104, "新增部门失败"),
    STAFF_DEPT_EDIT_ERROR(105, "编辑部门失败"),
    STAFF_DEPT_DEL_ERROR(106, "删除部门失败"),
    STAFF_DEPT_DEL_DEPT_ERROR(107, "删除部门失败--存在子级部门"),
    STAFF_DEPT_DEL_POSITION_ERROR(108, "删除部门失败--该部门存在职位信息"),

    /**
     * 员工管理-职位管理151-200
     */
    STAFF_POSITION_ERROR(151, "查询职位列表失败"),
    STAFF_POSITION_ADD_ERROR(152, "新增职位失败"),
    STAFF_POSITION_ADD_REPEAT_ERROR(153, "新增职位失败--已存在该职位"),
    STAFF_POSITION_EDIT_ERROR(154, "编辑职位失败"),
    STAFF_POSITION_EDIT_REPEAT_ERROR(155, "编辑职位失败--已存在该职位"),
    STAFF_POSITION_DEL_ERROR(156, "删除职位失败"),
    STAFF_POSITION_DEL_ROLE_ERROR(157, "删除职位失败--该职位存在角色信息"),
    TREE_INFO_ADD_PARENT_STAFF_GT1(158, "上级部门职位分配的人数大于1"),

    /**
     * 员工管理-角色管理201-250
     */
    STAFF_ROLE_ERROR(201, "查询角色列表失败"),
    STAFF_ROLE_INFO_ERROR(202, "查询角色详情失败"),
    STAFF_ROLE_ADD_ERROR(203, "新增角色失败"),
    STAFF_ROLE_EDIT_ERROR(204, "编辑角色失败"),
    STAFF_ROLE_DEL_ERROR(205, "删除角色失败"),
    STAFF_ROLE_IS_SAME_ERROR(206, "同一职位下角色名称不允许重复"),

    /**
     * 品牌管理-品牌列表251-300
     */
    BRAND_INFO_LIST_ERROR(251, "查询基础品牌列表失败"),
    BRAND_EXPAND_LIST_ERROR(252, "查询自有品牌列表失败"),
    BRAND_INFO_INFO_ERROR(253, "查询自有品牌详情失败"),
    BRAND_INFO_ADD_ERROR(254, "新增自有品牌失败"),
    BRAND_INFO_EDIT_ERROR(255, "编辑自有品牌失败"),
    BRAND_INFO_EDIT_STATUS_ERROR(256, "自有品牌停用启用失败"),
    BRAND_INFO_DEL_ERROR(257, "删除自有品牌失败"),
    BRAND_SAVE_ERROR(258, "云铺同步品牌新增接口失败"),
    BRAND_UPDATE_ERROR(259, "云铺同步品牌修改接口失败"),
    BRAND_DELETE_ERROR(260, "云铺同步品牌删除接口失败"),
    /**
     * 品牌管理-品牌业态301-350
     */
    BRAND_BUSINESS_TYPE_ERROR(301, "查询品牌业态失败"),
    BRAND_BUSINESS_TYPE_INFO_ERROR(302, "查询品牌业态详情失败"),
    BRAND_BUSINESS_TYPE_ADD_ERROR(303, "新增品牌业态失败"),
    BRAND_BUSINESS_TYPE_ADD_EXIST_ERROR(304, "新增品牌业态失败--业态名称已存在"),
    BRAND_BUSINESS_TYPE_EDIT_ERROR(305, "编辑品牌业态失败"),
    BRAND_BUSINESS_TYPE_EDIT_EXIST_ERROR(306, "编辑品牌业态失败--业态名称已存在"),
    BRAND_BUSINESS_TYPE_EDIT_STATUS_ERROR(307, "品牌业态停用启用失败"),
    BRAND_BUSINESS_TYPE_DEL_ERROR(308, "删除品牌业态失败"),
    BRAND_BUSINESS_TYPE_PARENT_NOT_DISABLE(309, "上级未启用"),
    BRAND_BUSINESS_TYPE_DISABLE_EXIST_CHILD(310, "存在子级，不能停用"),
    BRAND_BUSINESS_TYPE_DISABLE_ERROR(311, "被使用，不能停用"),

    /**
     * 品牌管理-租户业态351-400
     */
    BRAND_TENANT_TYPE_ERROR(351, "查询租户业态失败"),
    BRAND_TENANT_TYPE_INFO_ERROR(352, "查询租户业态详情失败"),
    BRAND_TENANT_TYPE_ADD_ERROR(353, "新增租户业态失败"),
    BRAND_TENANT_TYPE_EDIT_ERROR(354, "编辑租户业态失败"),
    BRAND_TENANT_TYPE_EDIT_EXIST_ERROR(355, "编辑租户业态失败--名称重复"),
    BRAND_TENANT_TYPE_EDIT_STATUS_ERROR(356, "租户业态停用启用失败"),
    BRAND_TENANT_TYPE_EDIT_STATUS_EXIST_ERROR(357, "租户业态启用失败--名称重复"),
    BRAND_TENANT_TYPE_EDIT_STATUS_DISABLE_ERROR(358, "租户业态停用失败--被使用"),
    BRAND_TENANT_TYPE_DEL_ERROR(359, "删除租户业态失败"),
    BRAND_TENANT_TYPE_DEL_DISABLE_ERROR(360, "删除租户业态失败--被使用"),
    BRAND_TENANT_LIST_ERROR(361, "查询租户列表失败"),
    BRAND_TENANT_INFO_ERROR(362, "查询租户详情失败"),
    BRAND_TENANT_ADD_ERROR(363, "新增租户失败"),
    BRAND_TENANT_EDIT_ERROR(364, "编辑租户失败"),
    BRAND_TENANT_EDIT_EXIST_ERROR(365, "编辑租户失败--启用手机号码不能重复"),
    BRAND_TENANT_EDIT_STATUS_ERROR(366, "租户停用启用失败"),
    BRAND_TENANT_EDIT_STATUS_EXIST_ERROR(367, "租户启用失败--启用手机号码不能重复"),
    BRAND_TENANT_DEL_ERROR(368, "删除租户失败"),
    BRAND_TENANT_RELATION_ERROR(369, "查询租户品牌对应关系失败"),
    BRAND_TENANT_RELATION_ADD_ERROR(370, "新增租户品牌对应关系失败"),
    BRAND_TENANT_RELATION_EDIT_ERROR(371, "新增租户品牌对应关系失败"),
    BRAND_TENANT_RELATION_EDIT_STATUS_ERROR(372, "租户品牌对应关系停用启用失败"),
    BRAND_TENANT_RELATION_DEL_ERROR(373, "删除租户品牌对应关系失败"),
    BRAND_TENANT_RELATION_MOBILE_ERROR(374, "手机号码不能为空"),
    BRAND_TENANT_NOT_START(375, "租户未启用"),
    BRAND_BRAND_EXPAND_NOT_START(376, "自有品牌未启用"),
    BRAND_BRAND_TENANT_EXIST(377, "该手机号以与此品牌绑定，无法继续绑定"),

    /**
     * 公共资料管理 401-450
     */
    FIELD_LIST_PAGE_ERROR(401, "自定义字段列表查询失败"),
    FIELD_SAVE_ERROR(402, "自定义字段列表保存失败"),
    FIELD_IS_NULL(403, "自定义字段id为空"),
    FIELD_INFO_DETAIL_ERROR(404, "查询自定义字段详情错误"),
    FIELD_RECID_IS_NULL(405, "自定义字段ID为空"),
    FIELD_RECID_DELETE_ERROR(406, "自定义字段删除错误"),
    FIELD_NAME_EXIST(407, "自定义字段已存在"),

    /**
     * 幢管理 451 - 500
     */
    MALL_BUILDINGZONE_LIST_PAGE_ERROR(451, "查询幢列表错误"),
    MALL_BUILDINGZONE_INFO_ERROR(452, "查询幢查看详情错误"),
    MALL_BUILDINGZONE_SAVE_ERROR(453, "保存幢失败"),
    MALL_BUILDINGZONE_ADD_BUILDINGZONENAME_EXIST(454, "幢名称已存在"),
    MALL_BUILDINGZONE_ADD_ERROR(455, "幢保存发生异常"),
    MALL_BUILDINGZONE_EDIT_BUILDINGZONENAME_EXIST(456, "幢名称已存在"),
    MALL_BUILDINGZONE_EDIT_STATUS_ERROR(457, "修改幢状态失败"),
    MALL_BUILDINGZONE_DELETE_ERROR(458, "删除幢失败"),
    MALL_BUILDINGZONE_DELETE_STATUS_NOT_ENABLED(459, "只能删除未启用的幢"),
    GET_BUILDINGZONE_LIST_BY_MALL_CODE(460, "根据mallCode查询所有的幢列表失败"),
    MALL_BUILDINGZONE_EDIT_STATUS_FLOOR_ERROR(461, "停用失败，存在未停用的楼层"),
    MALL_BUILDINGZONE_EDIT_STATUS_MALL_ERROR(462, "启用失败，该幢所属商业体未启用"),
    MALL_BUILDINGZONE_DISABLE_ERROR(463, "幢下存在层，不能进行下架处理"),
    MALL_BUILDINGZONE_CODE_EXIST(464, "自动生成的幢编号已存在,请重新保存提交"),
    MALL_BUILDINGZONE_SUBMIT(465, "幢提交审核发生异常"),
    MALL_BUILDINGZONE_CANCEL_SUBMIT(466, "幢取消审核发生异常"),
    MALL_BUILDINGZONE_STATUS_NO_TO_APPLY(467, "当前幢没有待审核的信息"),
    MALL_BUILDINGZONE_FLOOR_EXIST(468, "当前幢有楼层，无法删除"),

    /**
     * 物业资料层管理 501-550
     */
    GET_FLOOR_LIST_BY_BUILDINGZONE_CODE(501, "根据幢编号查询所有的层列表错误"),
    MALL_FLOOR_LIST_PAGE_ERROR(502, "查询层列表错误"),
    MALL_FLOOR_INFO_ERROR(503, "查询层查看详情错误"),
    MALL_FLOOR_SAVE_ERROR(504, "保存层失败"),
    MALL_FLOOR_ADD_FLOORNAME_EXIST(505, "楼层名称已存在"),
    MALL_FLOOR_CODE_EXIST(506, "楼层编号生成失败,请重新提交保存"),
    MALL_FLOOR_ADD_IMG_PATH_NOTEXIST(507, "文件路径不存在"),
    MALL_FLOOR_ADD_IMG_MOVE_ERROR(508, "移动图片发生错误"),
    MALL_FLOOR_EDIT_FLOORNAME_EXIST(509, "楼层名称已存在"),
    MALL_FLOOR_EDIT_IMG_PATH_NOTEXIST(510, "文件路径不存在"),
    MALL_FLOOR_ADD_IMG_COPY_ERROR(511, "复制图片发生错误"),
    MALL_FLOOR_EDIT_STATUS_ERROR(512, "修改层状态失败"),
    MALL_FLOOR_DELETE_ERROR(513, "删除层失败"),
    MALL_FLOOR_DELETE_STATUS_NOT_ENABLED(514, "只能删除未启用的层"),
    MALL_FLOOR_EDIT_STATUS_SHOP_ERROR(515, "停用失败，存在未停用的商铺"),
    MALL_FLOOR_EDIT_STATUS_BUILDINGZONE_ERROR(516, "启用失败，该楼层所属幢未启用"),
    MALL_FLOOR_DISABLE_ERROR(517, "下架失败，该层下存在商铺"),
    FLOOR_WITHDRAW_ERROR(518, "撤回层失败"),
    FLOOR_UPDATE_ERROR(519, "修改层失败"),
    FLOOR_PLAN_HANDLE_ERROR(520, "处理层平面图失败"),
    MALL_FLOOR_SUBMIT(520, "楼层提交审核发生异常"),
    MALL_FLOOR_CANCEL_SUBMIT(520, "楼层取消审核发生异常"),
    FLOOR_STATUS_NO_TO_APPLY(729, "当前楼层没有待审核的信息"),
    MALL_FLOOR_DELETE_ERROR_BECAUSE_OF_SHOP_EXIST(730, "该楼层存在商铺,无法删除"),
    /*物业资料-商铺管理 551-600*/
    MALL_SHOP_LIST_PAGE_ERROR(551, "查询商铺列表失败"),
    SHOP_CODE_IS_NULL(552, "商铺编号为空"),
    GET_SHOP_BY_SHOP_CODE(553, "查看商铺详情失败"),
    EDIT_SHOP_ERROR(554, "商铺编辑失败"),
    DEL_SHOP_ERROR(555, "商铺删除失败"),
    EDIT_SHOP_STATUS_ERROR(556, "商铺启用停用错误"),
    EDIT_SHOP_STATUS_FOR_DISABLE_ERROR(557, "该商铺具有闪铺，不能停用"),
    EDIT_SHOP_STATUS_FOR_ENABLE_ERROR(558, "该商铺所属层已停用或未启用，不能启用"),
    SHOP_STATUS_IS_NULL(559, "传入商铺状态为空"),
    SHOP_INFO_IS_NULL(560, "该商铺不存在"),
    DEL_SHOP_ERROR_FOR_STATUS(561, "商铺删除失败-只能删除未启用的商铺"),
    EDIT_SHOP_ERROR_FOR_SHOP_NAME(562, "商铺编辑失败-商铺名称已存在"),
    EDIT_SHOP_ERROR_FOR_SHOP_NO(563, "商铺编辑失败-门牌号已存在"),
    MALL_SHOP_ADD_ERROR(564, "商铺添加失败"),
    SHOP_INFO_HANDLE_IMAGE_ERROR(565, "添加商铺失败-图片处理异常"),
    SHOP_INFO_IMAGE_MISS(566, "图片未上传"),
    SHOP_PIC_HANDLE_ERROR(567, "图片处理异常"),
    EDIT_SHOP_FOR_DISABLE_ERROR(568, "修改失败--该商铺具有闪铺,不能从空铺变成满铺、停租"),
    MALL_SHOP_SAVE_NAME_EXIST(569, "商铺名称重复"),
    MALL_SHOP_SAVE_NO_EXIST(570, "门牌号重复"),
    MALL_SHOP_DISABLE_ERROR(571, "被使用，不能下架"),
    MALL_SHOP_OVERALL_ERROR(578, "商铺全景图处理异常"),
    MALL_SHOP_PARENT_ERROR(579, "商业体幢层列表查询失败"),
    MALL_SHOP_ADD_CODE_EXIST(580, "商铺编号生成失败，请重新提交"),

    /*物业资料-闪铺管理 601-650*/
    MALL_POPUP_STORE_LIST_PAGE_ERROR(601, "查询闪铺列表失败"),
    GET_MALL_POPUP_STORE_INFO_ERROR(602, "查看闪铺详情失败"),
    EDIT_MALL_POPUP_STORE_ERROR(603, "闪铺编辑失败"),
    EDIT_MALL_POPUP_STORE_STATUS_ERROR(604, "闪铺状态修改失败"),
    DELETE_MALL_POPUP_STORE_ERROR(605, "闪铺状态修改失败"),

    /**
     * 商铺业态： 651-700
     */
    MALL_BUSINESSTYPE_ADD_ERROR(651, "商铺业态生成失败"),
    Mall_BUSINESSTYPE_INFO_SAVE_NAME_EXIST(652, "业态名称重复"),
    Mall_BUSINESSTYPE_DEL_ERROR_FOR_STATUS(653, "删除失败-只能删除未启用的商铺业态 "),
    Mall_BUSINESSTYPE_DEL_ERROR_FOR_CODES(654, "删除失败-不能删除有子级元素的业态 "),
    MALL_BUSINESSTYPE_INFO_LIST_ERROR(655, "查询商铺业态列表失败"),
    MALL_BUSINESSTYPE_INFO_DETAIL_ERROR(655, "查询商铺业态详情失败"),
    MALL_BUSINESSTYPE_CODE_NULL(656, "商铺业态code为空"),
    MALL_BUSINESSTYPE_DEL_ERROR(657, "商铺业态删除异常"),
    MALL_BUSINESSTYPE_EXIST_CHILD(658, "存在子级，不能删除"),
    MALL_BUSINESSTYPE_DISABLE_EXIST_CHILD(659, "存在子级，不能停用"),
    MALL_BUSINESSTYPE_DISABLE_ERROR(660, "被使用，不能停用"),
    MALL_BUSINESSTYPE_INFO_EDIT_ERROR(661, "编辑商铺业态失败"),
    MALL_BUSINESSTYPE_PARENT_NOT_DISABLE(662, "上级未启用"),

    /**
     * 商业体:701-750
     */
    MALL_INFO_QUERY_ERROR(700, "商业体列表查询错误"),
    MALL_INFO_INIT_ERROR(701, "获取商业体初始化暑假失败"),
    MALL_INFO_SAVE_ERROR(702, "商业体保存异常"),
    MALL_NAME_EXISTS(703, "商业体名称已经存在"),
    MALL_PIC_HANDLE_ERROR(704, "商业体图片处理异常"),
    MALL_INFO_DETAIL_ERROR(705, "商业体详情报错"),
    MALL_INFO_STATUS_OFF_ON(706, "未启用只能修改成启用"),
    MALL_INFO_STATUS_ON_UNUSERD(707, "启用只能修改成停用"),
    MALL_INFO_STATUS_UNUSERD_ON(708, "停用只能修改成启用"),
    MALL_INFO_STATUS_CHANGE_ERROR(709, "状态修改异常"),
    MALL_INFO_DEL_ERROR(710, "商业体删除异常"),
    MALL_INFO_STATUS_ERROR(711, "只有未启用的情况允许删除"),
    MALL_INFO_IMAGE_MISS(712, "图片未上传,无法启用"),
    MALL_INFO_CITIES_ERROR(713, "获取已经启用的城市出错"),
    MALL_CODE_IS_NULL(714, "mallCode为空"),
    BUILDINGZONE_CODE_IS_NULL(715, "buildingzoneCode为空"),
    MALL_STATUS_IS_FALSE(716, "商业体此状态下不能删除"),
    MALL_INFO_APPROVE_DETAIL_ERROR(717, "商业体审核详情异常"),
    MALL_INFO_APPROVE_ERROR(718, "商业体审核异常"),
    MALL_INFO_OFFTHESHELF_ERROR(719, "商业体下架异常"),
    MALL_INFO_CAN_NOT_OFFTHESHELF(720, "商业体下存在正在使用的幢"),
    MALL_INFO_UPDATE_ERROR(721, "商业体修改失败"),
    MALL_TYPE_QUERY_ERROR(722, "商业体类型查询异常"),
    MALL_TYPE_FIELD_QUERY_ERROR(723, "商业体附加字段查询异常"),
    MALL_BRAND_QUERY_ERROR(724, "商场品牌查询异常"),
    MALL_CODE_EXIST(725, "商业体主键已存在,请重新保存"),
    MALL_CUSTOM_CODE_EXIST(726, "商业体代码已存在"),
    MALL_CANCEL_SUBMIT_ERROR(727, "商业体取消审核发生异常"),
    MALL_SUBMIT_ERROR(728, "商业体提交审核发生异常"),
    MALL_STATUS_NO_TO_APPLY(729, "当前商业体没有待审核的信息"),
    MALL_DEL_ERROR(730, "当前商业体下存在幢，无法删除"),

    /*物业资料751-800*/
    MALL_SHOP_SUPPORT_ITEM_EXISTS(751, "配套参数已存在"),
    MALL_SHOP_SUPPORT_ITEM_DEL_STATUS_ERROR(752, "只能删除未启用的职位"),
    MALL_SHOP_SUPPORT_ITEM_OFF_ON(753, "未启用只能修改成启用"),
    MALL_SHOP_SUPPORT_ITEM_ON_UNUSERD(754, "状态启用只能修改成停用"),
    MALL_SHOP_SUPPORT_ITEM_UNUSERD_ON(755, "状态停用只能修改成启用"),
    MALL_SHOP_SUPPORT_ITEM_QUERY_ERROR(756, "配套参数查询失败"),
    MALL_SHOP_SUPPORT_ITEM_ID_MISS(757, "缺少配套参数编号"),
    MALL_SHOP_SUPPORT_ITEM_SAVE_ERROR(758, "保存配套参数失败"),
    MALL_SHOP_SUPPORT_ITEM_DEL_ERROR(759, "配套参数删除失败"),
    MALL_SHOP_SUPPORT_ITEM_STATUS_CHANGE_ERROR(760, "配套参数状态修改异常"),
    MALL_SHOP_SUPPORT_ITEM_DETAIL_QUERY_ERROR(761, "配套参数详情查询失败"),
    REC_ID_IS_NULL(762, "自定义字段id为空"),

    /**
     * 基本资料管理 801-850
     */
    REGION_COUNTRY_LIST_ERROR(801, "获取国家列表失败"),
    REGION_CODE_ERROR(802, "传入code为空"),
    REGION_PROVINCE_LIST_ERROR(803, "获取省列表失败"),
    REGION_CITY_LIST_ERROR(804, "获取城市列表失败"),
    REGION_DISTRICT_LIST_ERROR(805, "获取区域列表失败"),
    REGION_LIST_ERROR(806, "获取地区列表失败"),
    REGION_DISTRIC_CBD_LIST_ERROR(807, "商圈名称列表查询失败"),

    /**
     * 销售等级 851-900
     */
    PJ_SALE_LEVEL_ADD_ERROR(851, "销售等级数据同步失败"),

    /**
     * 前台文章信息 901-950
     */
    FRONT_ARTICLE_CAT_PAGE_ERROR(901, "获取文章分类列表失败"),
    FRONT_ARTICLE_CAT_ADD_ERROR(901, "新增文章分类失败"),
    FRONT_ARTICLE_CAT_EDIT_ERROR(901, "编辑文章分类失败"),
    FRONT_ARTICLE_CAT_EDIT_STATUS_ERROR(901, "文章分类停用启用失败"),
    FRONT_ARTICLE_CAT_EDIT_STATUS_EXIST_ERROR(901, "文章分类停用失败--存在启用的文章信息"),
    FRONT_ARTICLE_CAT_DEL_ERROR(901, "删除文章分类失败"),
    FRONT_ARTICLE_INFO_PAGE_ERROR(901, "获取文章列表失败"),
    FRONT_ARTICLE_INFO_DETAIL_ERROR(901, "获取文章详情失败"),
    FRONT_ARTICLE_INFO_ADD_ERROR(901, "新增文章失败"),
    FRONT_ARTICLE_INFO_EDIT_ERROR(901, "编辑文章失败"),
    FRONT_ARTICLE_INFO_EDIT_STATUS_ERROR(901, "文章停用启用失败"),
    FRONT_ARTICLE_INFO_EDIT_STATUS_EXIST_ERROR(901, "文章启用失败--改文章分类未启用"),
    FRONT_ARTICLE_INFO_DEL_ERROR(901, "删除文章失败"),

    /**
     * 图片上传 951-960
     */
    IMAGE_PATH_IS_NULL(951, "文件路径不存在"),
    IMAGE_PATH_ERROR(952, "保存图片错误"),

    /**
     * 运营商 961-1000
     */
    OWNER_LIST_ERROR(961, "运营商列表查询异常"),
    OWNER_EDIT_ERROR(962, "运营商编辑异常"),
    OWNER_DEL_ERROR(963, "运营商删除异常"),
    OWNER_INFO_ERROR(964, "查询运营商信息异常"),
    OWNER_NAME_EXIST(965, "运营商名称已存在"),

    /**
     * 系统菜单 1000-1050
     */
    PRIVILEGE_CODE_EXIST(1001,"权限代码已存在"),
    PRIVILEGE_EDIT_FAIL(1002,"权限编辑异常"),
    PRIVILEGE_DEL_FAIL(1003,"权限删除异常"),
    PRIVILEGE_CHANGE_STATUS_FAIL(1004,"权限修改异常"),
    PRIVILEGE_TREE_FAIL(1003,"权限查询异常"),

    /**
     * 通用异常1050-1100
     */
    COMMON_FILE_NOT_EXIST(1051,"文件不存在"),
    IMAGE_NOT_EXIST(1052,"图片不存在"),
    IMAGE_HANDLE_ERROR(1053,"图片处理异常");

    private final Integer errorCode;
    private final String errorMsg;

    private ErrorCodes(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public int getErrorCode() {
        return errorCode;
    }

}
