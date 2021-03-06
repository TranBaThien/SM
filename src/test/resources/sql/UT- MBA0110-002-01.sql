INSERT INTO `tbl100`
(`APARTMENT_ID`,
`PROPERTY_NUMBER`,
`APARTMENT_NAME`,
`APARTMENT_NAME_PHONETIC`,
`ZIP_CODE`,
`CITY_CODE`,
`ADDRESS`,
`REPASSWORD_MAIL_ADDRESS`,
`MAIL_ADDRESS`,
`NEWEST_NOTIFICATION_NO`,
`NEWEST_NOTIFICATION_NAME`,
`NEWEST_NOTIFICATION_ADDRESS`,
`NOTIFICATION_KBN`,
`SUPPORT`,
`NEXT_NOTIFICATION_DATE`,
`BUILD_YEAR`,
`BUILD_MONTH`,
`BUILD_DAY`,
`FLOOR_NUMBER`,
`HOUSE_NUMBER`,
`SITE_AREA`,
`TOTAL_FLOOR_AREA`,
`BUILDING_AREA`,
`BUILDING_STRUCTURE`,
`REGISTRATION_NO`,
`REGISTRATION_ADDRESS`,
`REGISTRATION_HOUSE_NO`,
`REAL_ESTATE_NO`,
`CITY_NAME`,
`APARTMENT_LOST_FLAG`,
`PRELIMINARY1`,
`PRELIMINARY2`,
`PRELIMINARY3`,
`PRELIMINARY4`,
`PRELIMINARY5`,
`DELETE_FLAG`,
`UPDATE_USER_ID`,
`UPDATE_DATETIME`)
VALUES
('1000000001',
'0008',
'abc',
'abc phonetic',
'12345678',
'111111',
'tokyo',
'abcd@gmail.com',
'vutran26999@gmail.com',
'123456',
'osaka',
'tokyo',
'1',
'1',
'',
'2020',
'02',
'10',
'111',
'222',
'0.1',
'0.1',
'0.1',
'0101010101',
'000008',
'kyoto',
'osaka',
'1111111111113',
'kyoto',
'1',
'00001',
'00002',
'0000003',
'00004',
'00005',
'0',
'G00000011',
null);

INSERT INTO `tbl300`
(`PROGRESS_RECORD_NO`,
`APARTMENT_ID`,
`CORRESPOND_DATE`,
`TYPE_CODE`,
`CORRESPOND_TYPE_CODE`,
`NOTICE_TYPE_CODE`,
`RELATED_NUMBER`,
`PROGRESS_RECORD_OVERVIEW`,
`PROGRESS_RECORD_DETAIL`,
`SUPPORT_CODE`,
`NOTIFICATION_METHOD_CODE`,
`DELETE_FLAG`,
`UPDATE_USER_ID`,
`UPDATE_DATETIME`)
VALUES
('1000000003',
 '1000000001',
 '201912040529',
 '4',
 NULL,
 '1',
 '123456',
 'Test progress',
 'test progress record details',
 '1',
 '1',
 '0',
 '1000000010',
 '2019-12-24 19:42:15'),
 ('1000000002',
 '1000000001',
 '201912240615',
 '4',
 NULL,
 '1',
 '1000000002',
 'Test progress',
 'test progress record details',
 '1',
 '1',
 '0',
 '1000000010',
 '2019-12-24 18:55:14');