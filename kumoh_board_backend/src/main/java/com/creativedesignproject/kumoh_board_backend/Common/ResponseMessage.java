package com.creativedesignproject.kumoh_board_backend.Common;

public interface ResponseMessage {
    //http 200
    String SUCCESS = "Success.";

    //http 400
    String VALIDATION_FAIL = "Validation failed.";
    String DUPLICATE_NICKNAME = "Duplicate Nickname.";
    String DUPLICATE_EMAIL = "Duplicate Email.";
    String DUPLICATE_ID = "Duplicate ID.";
    String DUPLICATE_CATEGORY_NAME = "Duplicate Category Name.";

    //http 401
    String SIGN_IN_FAIL = "Login information mismatch.";
    String CERTIFICATION_FAIL = "Certification failed.";
    String NO_USER_ROLE = "No User Role.";
    String NO_ADMIN_ROLE = "No Admin Role.";
    String NOT_EXISTED_USER = "Not Existed User.";
    String NOT_EXISTED_CATEGORY = "Not Existed Category.";

    //http 500
    String MAIL_FAIL = "Mail Send failed.";
    String DATABASE_ERROR = "Database error.";
}