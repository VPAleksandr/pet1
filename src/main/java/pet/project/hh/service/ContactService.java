package pet.project.hh.service;

import pet.project.hh.Dto.ContactDto;
import pet.project.hh.Dto.ContactTypeDto;
import pet.project.hh.models.Resume;

import java.util.List;

public interface ContactService {

    List<ContactDto> getContactsByResumeId(Long resumeId);

    List<ContactTypeDto> getAllContactTypes();

    void deleteContactsForResume(Resume resume);

    void saveContactForResume(ContactDto contactDto, Resume resume);

    void deleteContactsForResume(Long resumeId);
}
