//package com.faniherfeei.demo1dashboard.dto;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * بدنه درخواست ایجاد یا ویرایش گزارش (POST /reports و PUT /reports/{id}).
// * دقیقاً منطبق با آبجکت data ساخته‌شده در تابع proceedSaveReport فرانت.
// */
//public record ReportRequest(
//        String title,
//        String description,
//        String reportId,                 // شناسه یکتای گزارش وارد شده توسط کاربر (مثلاً RPT-1405-001)
//        String departmentId,
//        String departmentName,
//        List<Map<String, String>> rows   // هر عضو لیست یک سطر جدول است؛ کلید = نام ستون، مقدار = مقدار سلول
//) {
//}