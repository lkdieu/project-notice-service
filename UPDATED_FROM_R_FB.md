  UPDATE FROM FEEDBACK

- Use the JpaRepository to query data
   + Optional<Notice> findByIdAndEndDateGreaterThanEqualAndIsEnableTrue(Long id, Date date);
   + Page<Notice> findByUserId(Long id, Pageable pageable);
   + Page<Notice> findAllByEndDateGreaterThanEqualAndIsEnableIsTrue(Date date, Pageable pageable);

- Break down methods, to perform functions
- Create class constants to manage message, field

- Using annotations in hibernate
   + @CreatedDate, @Temporal(TemporalType.TIMESTAMP)
   + @JsonIgnore: This field-level annotation is also used to subtract fields during Serialization and Deserialization of JSON data.
   + Use annotations to create relationships

- Create exception handling classes
  + GlobalExceptionHandler: validation error exception handling
  + NoticeServiceException: Exception handling error FORBIDDEN, INTERNAL_SERVER_ERROR
  + ResourceNotFoundException: Exception handling error does not exist

- Spring boot cache to store data in in memory cache increases data query speed
  +@Cacheable(cacheNames = "notice", key = "#id")
  +@CacheEvict(cacheNames = "notice", key = "#id")
  +@CachePut(cacheNames = "notice", key = "#id")