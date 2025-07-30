package pet.project.hh.service.impl;

import pet.project.hh.Dto.ContactDto;
import pet.project.hh.Dto.ContactTypeDto;
import pet.project.hh.models.Contact;
import pet.project.hh.models.ContactType;
import pet.project.hh.models.Resume;
import pet.project.hh.repository.ContactRepository;
import pet.project.hh.repository.ContactTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ContactServiceImplTest {

    @InjectMocks
    private ContactServiceImpl contactService;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactTypeRepository contactTypeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveContactForResume() {
        // Подготовка данных
        Long resumeId = 1L;
        Resume resume = new Resume();
        resume.setId(resumeId);

        Long contactTypeId = 1L;
        ContactType contactType = new ContactType();
        contactType.setId(contactTypeId);
        contactType.setType("Email");

        ContactDto contactDto = new ContactDto();
        contactDto.setId(1L);
        contactDto.setContactValue("email@example.com");
        contactDto.setTypeId(contactTypeId);

        Contact savedContact = Contact.builder()
                .id(contactDto.getId())
                .contactValue(contactDto.getContactValue())
                .resume(resume)
                .type(contactType)
                .build();

        // Настройка мока для contactTypeRepository
        when(contactTypeRepository.findById(contactTypeId)).thenReturn(Optional.of(contactType));

        // Настройка мока для contactRepository
        when(contactRepository.save(any(Contact.class))).thenReturn(savedContact);

        // Выполнение метода
        contactService.saveContactForResume(contactDto, resume);

        // Проверка, что метод репозитория был вызван с правильным объектом
        verify(contactRepository).save(argThat(contact ->
                contact.getId().equals(contactDto.getId()) &&
                        contact.getContactValue().equals(contactDto.getContactValue()) &&
                        contact.getResume().equals(resume) &&
                        contact.getType().equals(contactType)
        ));

        // Дополнительная проверка (если нужно возвращать сохраненный контакт)
        // Здесь можно проверить через сервис, если есть метод для получения контакта

    }

    @Test
    void testGetContactsByResumeId() {
        // Подготовка данных
        Long resumeId = 1L;
        Resume resume = new Resume();
        resume.setId(resumeId);

        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setContactValue("email@example.com");
        contact1.setResume(resume);
        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setContactValue("phone123");
        contact2.setResume(resume);

        ContactType contactType = new ContactType();
        contactType.setId(1L);
        contactType.setType("Email");
        contact1.setType(contactType);
        contact2.setType(contactType);

        // Мокирование поведения репозитория
        when(contactRepository.findByResumeId(resumeId))
                .thenReturn(Arrays.asList(contact1, contact2));

        // Выполнение теста
        List<ContactDto> result = contactService.getContactsByResumeId(resumeId);

        // Проверка результата
        assertEquals(2, result.size());
        assertEquals("email@example.com", result.get(0).getContactValue());
        assertEquals("phone123", result.get(1).getContactValue());
        assertEquals("Email", result.get(0).getTypeName());
        assertEquals("Email", result.get(1).getTypeName());
    }

    @Test
    void testDeleteContactsForResume() {
        // Подготовка данных
        Resume resume = new Resume();
        resume.setId(1L);

        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setContactValue("email@example.com");
        contact1.setResume(resume);

        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setContactValue("phone123");
        contact2.setResume(resume);

        ContactType contactType = new ContactType();
        contactType.setId(1L);
        contactType.setType("Email");
        contact1.setType(contactType);
        contact2.setType(contactType);

        // Настройка мока перед удалением
        when(contactRepository.findByResumeId(resume.getId())).thenReturn(Arrays.asList(contact1, contact2));

        // Проверка до удаления через сервис
        List<?> contactsBefore = contactService.getContactsByResumeId(resume.getId());
        assertEquals(2, contactsBefore.size()); // Проверяем количество

        // Выполнение метода
        contactService.deleteContactsForResume(resume);

        // Настройка мока после удаления
        when(contactRepository.findByResumeId(resume.getId())).thenReturn(List.of());

        // Проверка после удаления через сервис
        List<?> contactsAfter = contactService.getContactsByResumeId(resume.getId());
        assertEquals(0, contactsAfter.size()); // Ожидаем пустой список

        // Проверка, что метод удаления был вызван
        verify(contactRepository).deleteByResume(resume);
    }

    @Test
    void testGetAllContactTypes() {
        // Подготовка данных
        ContactType contactType1 = new ContactType();
        contactType1.setId(1L);
        contactType1.setType("Email");

        ContactType contactType2 = new ContactType();
        contactType2.setId(2L);
        contactType2.setType("Phone");

        // Мокирование поведения репозитория
        when(contactTypeRepository.findAll()).thenReturn(Arrays.asList(contactType1, contactType2));

        // Выполнение теста
        List<ContactTypeDto> result = contactService.getAllContactTypes();

        // Проверка результата
        assertEquals(2, result.size());
        assertEquals("Email", result.get(0).getType());
        assertEquals("Phone", result.get(1).getType());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void testDeleteContactsForResumeById() {
        // Подготовка данных
        Long resumeId = 1L;
        Resume resume = new Resume();
        resume.setId(resumeId);

        Contact contact1 = new Contact();
        contact1.setId(1L);
        contact1.setContactValue("email@example.com");
        contact1.setResume(resume);

        Contact contact2 = new Contact();
        contact2.setId(2L);
        contact2.setContactValue("phone123");
        contact2.setResume(resume);

        ContactType contactType = new ContactType();
        contactType.setId(1L);
        contactType.setType("Email");
        contact1.setType(contactType);
        contact2.setType(contactType);

        // Настройка мока перед удалением
        when(contactRepository.findByResumeId(resumeId)).thenReturn(Arrays.asList(contact1, contact2));

        // Проверка до удаления
        List<?> contactsBefore = contactService.getContactsByResumeId(resumeId);
        assertEquals(2, contactsBefore.size());

        // Выполнение метода
        contactService.deleteContactsForResume(resumeId);

        // Настройка мока после удаления
        when(contactRepository.findByResumeId(resumeId)).thenReturn(List.of());

        // Проверка после удаления
        List<?> contactsAfter = contactService.getContactsByResumeId(resumeId);
        assertEquals(0, contactsAfter.size());

        // Проверка, что метод удаления был вызван
        verify(contactRepository).deleteByResume_Id(resumeId);
    }


}