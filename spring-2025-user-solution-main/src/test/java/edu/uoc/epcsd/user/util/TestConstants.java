package edu.uoc.epcsd.user.util;

public final class TestConstants {

  public static final Long TEST_ITEM_ID = 1L;
  public static final Long TEST_SESSION_ID = 1L;
  public static final String TEST_DESCRIPTION = "Test description";
  public static final Long TEST_LATITUDE = 10L;
  public static final Long TEST_LONGITUDE = 20L;
  public static final String TEST_LINK = "https://item.com/test";

  public static final Long TEST_ITEM_ID2 = 1L;
  public static final Long TEST_SESSION_ID2 = 1L;
  public static final String TEST_DESCRIPTION2 = "Test description 2";
  public static final Long TEST_LATITUDE2 = 11L;
  public static final Long TEST_LONGITUDE2 = 21L;
  public static final String TEST_LINK2 = "https://item.com/test";

  public static final Long USER_ID = 1L;
  public static final String USER_FULL_NAME = "Test User";
  public static final String USER_EMAIL = "test@example.com";
  public static final String USER_PASSWORD = "password";
  public static final String USER_PHONE_NUMBER = "123456789";

  public static final Long SESSION_ID = 2L;
  public static final String SESSION_DESCRIPTION = "Test Digital Session";
  public static final String SESSION_LOCATION = "Test Location";
  public static final String SESSION_LINK = "https://example.com";

  public static final Long NON_EXISTING_USER_ID = 99L;

  public static final String BASE_PACKAGE = "edu.uoc.epcsd.user";
  public static final String DOMAIN_MODEL_PACKAGE = BASE_PACKAGE + ".domain";
  public static final String DOMAIN_EXCEPTION_PACKAGE = DOMAIN_MODEL_PACKAGE + ".exception..";
  public static final String DOMAIN_REPOSITORY_PACKAGE = DOMAIN_MODEL_PACKAGE + ".repository..";
  public static final String DOMAIN_SERVICE_PACKAGE = DOMAIN_MODEL_PACKAGE + ".service..";
  public static final String APPLICATION_PACKAGE = BASE_PACKAGE + ".application..";
  public static final String INFRASTRUCTURE_PACKAGE = BASE_PACKAGE + ".infrastructure";
  public static final String INFRASTRUCTURE_JPA_PACKAGE = INFRASTRUCTURE_PACKAGE + ".repository.jpa..";
  public static final String INFRASTRUCTURE_REST_PACKAGE = INFRASTRUCTURE_PACKAGE + ".repository.rest..";

}
