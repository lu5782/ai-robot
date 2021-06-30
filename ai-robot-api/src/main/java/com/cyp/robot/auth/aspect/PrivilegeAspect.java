package com.cyp.robot.auth.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Aspect
public class PrivilegeAspect {
//    @Autowired
//    private PrivilegeInfoService privilegeInfoService;

    @Around(value = "execution(* com.canyuepo.fitness..*.controller..*.*(..)) && @annotation(privilege)", argNames = "joinPoint,privilege")
    public Object doAround(final ProceedingJoinPoint joinPoint, Privilege privilege) throws Throwable {
        String[] codes = privilege.value();
        Privilege success = joinPoint.getTarget().getClass().getMethod("success").getAnnotation(Privilege.class);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        codes = success.value();
        String privilegeCodes = ",paas_staff_manage,paas_staff_info,paas_staff_list,paas_staff_edit,paas_staff_privilege,paas_staff_view,paas_mall,paas_mall_mall,paas_mall_mall_view,paas_mall_mall_edit,paas_mall_buildingzone,paas_mall_buildingzone_view,paas_mall_buildingzone_edit,paas_mall_floor,paas_mall_floor_view,paas_mall_floor_edit,pass_rent,pass_rent_shop_annual,paas_mall_annual,paas_mall_annual_view,paas_mall_annual_edit,paas_mall_store,paas_mall_store_view,paas_mall_store_edit,paas_mall_ad,paas_mall_ad_view,paas_mall_ad_edit,staff_ppt_privilege,paas_store_order,paas_bi_report,paas_brand_info,paas_investment,paas_demand_processing,paas_dynamic_push,paas_analysis,paas_mall_retailing,paas_staff_messages,paas_ptd_store_order,paas_order_info,paas_property,paas_brand,paas_rent_view,paas_rent_edit,paas_retailing_order,paas_ptd_retailing_order,paas_retailing_order_info,";
//        String privilegeCodes = this.privilegeInfoService.getUserAllPrivilege(StaffInfoHelper.getLoginUserId(request));
        for (String p : codes) {
            if (privilegeCodes.contains(p)) {
                return joinPoint.proceed(joinPoint.getArgs());
            }
        }
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return null;
    }
}
