//package com.faniherfeei.demo1dashboard.controller;
//
//import com.faniherfeei.demo1dashboard.dto.*;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
///**
// * کنترلر مربوط به کاربر جاری.
// *
// * متناظر با API_CONFIG.endpoints در فرانت:
// *   myDepartments -> GET /user/my-departments : بخش‌های مجاز کاربر جاری (استفاده در loadUserDepartments،
// *                    برای پر کردن select «بخش مربوطه» در فرم ایجاد/ویرایش گزارش)
// *   currentUser   -> GET /user/me : اطلاعات کاربر جاری (استفاده در loadCurrentUser، برای تشخیص مالکیت گزارش‌ها)
// *
// * توضیح فرانت: این اطلاعات (بخش‌های مجاز) از سامانه «کاربر ارشد» در پایگاه‌داده مشترک تعیین می‌شود؛
// * یعنی احتمالاً Service این کنترلر باید با آن سامانه/جدول مشترک ارتباط برقرار کند.
// */
//@RestController
//@RequestMapping("/api/reports-service/user")
//public class UserController {
//
//    /**
//     * GET /api/reports-service/user/my-departments
//     * بخش‌هایی که کاربر جاری مجاز به ثبت گزارش برای آن‌هاست.
//     * اگر کاربر هیچ بخشی نداشته باشد، فرانت انتظار یک لیست خالی را دارد (نه خطا).
//     */
//    @GetMapping("/my-departments")
//    public ResponseEntity<List<DepartmentResponse>> getMyDepartments(Authentication authentication) {
//        // TODO: پیاده‌سازی توسط سرویس
//        return ResponseEntity.ok(null);
//    }
//
//    /**
//     * GET /api/reports-service/user/me
//     * اطلاعات کاربر جاری (حداقل شماره پرسنلی که برای تشخیص مالکیت گزارش‌ها لازم است)
//     */
//    @GetMapping("/me")
//    public ResponseEntity<UserResponse> getCurrentUser(Authentication authentication) {
//        // TODO: پیاده‌سازی توسط سرویس
//        return ResponseEntity.ok(null);
//    }
//}
