package pet.project.hh.service.impl;

import pet.project.hh.Dto.ContactDto;
import pet.project.hh.Dto.ContactTypeDto;
import pet.project.hh.models.Contact;
import pet.project.hh.models.Resume;
import pet.project.hh.repository.ContactRepository;
import pet.project.hh.repository.ContactTypeRepository;
import pet.project.hh.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactTypeRepository contactTypeRepository;

    @Override
    public List<ContactDto> getContactsByResumeId(Long resumeId) {
        return contactRepository.findByResumeId(resumeId)
                .stream()
                .map(c -> ContactDto.builder()
                        .id(c.getId())
                        .typeName(c.getType().getType())
                        .contactValue(c.getContactValue())
                        .build())
                .toList();
    }

    @Override
    public List<ContactTypeDto> getAllContactTypes() {
        return contactTypeRepository.findAll()
                .stream()
                .map(c -> ContactTypeDto.builder()
                        .id(c.getId())
                        .type(c.getType())
                        .build())
                .toList();
    }

    @Override
    public void deleteContactsForResume(Resume resume) {
        contactRepository.deleteByResume(resume);
    }

    @Override
    public void saveContactForResume(ContactDto contactDto, Resume resume) {
        Contact contact = Contact.builder()
                .id(contactDto.getId())
                .contactValue(contactDto.getContactValue())
                .resume(resume)
                .type(contactTypeRepository.findById(contactDto.getTypeId()).get())
                .build();
        contactRepository.save(contact);
    }

    @Override
    public void deleteContactsForResume(Long resumeId) {
        contactRepository.deleteByResume_Id(resumeId);
    }

}
