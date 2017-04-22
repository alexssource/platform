package com.platform.data.test.inregration.repository;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 12/20/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "development")
@ContextConfiguration(locations = {"classpath:beans.xml", "classpath:persistence.xml"})
public class CrudTestIntegration {

    @Autowired
    private TraderRepository repository;


    @Test
    public void testInsert() {
        long recordsCount = getRecordsCount();
        Trader newEntity = repository.save(createNewEntity());
        assertNotNull("The entity id can't be null", newEntity.getId());
        long updatedSize = repository.count();
        assertEquals(recordsCount + 1, updatedSize);
    }


    @Test
    public void testUpdate() {
        Trader entity = repository.save(createNewEntity());
        Long entityId = entity.getId();
        assertNotNull("The entity id can't be null", entity.getId());
        long recordsCount = getRecordsCount();
        Trader updatedEntity = repository.save(entity);
        Long updatedEntityId = updatedEntity.getId();
        assertNotNull(updatedEntityId);
        assertEquals(entityId, updatedEntityId);
        assertEquals(recordsCount, repository.count());
    }


    @Test
    public void testFindAll() throws InterruptedException {
        long initialRecordsCount = getRecordsCount();
        repository.save(createNewEntity());
        Thread.sleep(3000L);
        repository.save(createNewEntity());
        long resultRecordsCount = getRecordsCount();
        assertEquals(initialRecordsCount + 2, resultRecordsCount);
    }


    @Test
    public void testFindOne() {
        Trader entity = repository.save(createNewEntity());
        assertNotNull(entity.getId());
        Trader foundEntity = repository.findOne(entity.getId());
        assertNotNull(foundEntity);
        assertEquals(entity.getId(), foundEntity.getId());
    }


    @Test
    public void testFindOneNotFound() {
        Trader foundEntity = repository.findOne(10240L);
        assertNull(foundEntity);
    }


    @Test
    public void testDelete() {
        Trader entity = repository.save(createNewEntity());
        long recordsCount = getRecordsCount();
        repository.delete(entity);
        long resultRecordsCount = getRecordsCount();
        assertEquals(recordsCount - 1, resultRecordsCount);
    }


    private long getRecordsCount() {
        return repository.count();
    }


    private Trader createNewEntity() {
        String uniqueEmail = UserTestHelper.generateUniqueEmail();
        return new Trader(uniqueEmail, "assddsda", true, ZonedDateTime.now(), Role.ROLE_USER, false,
                ZonedDateTime.now(), ZonedDateTime.now());
    }
}
