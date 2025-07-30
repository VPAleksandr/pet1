'use strict';

document.addEventListener('DOMContentLoaded', () => {
    console.log('DOM fully loaded, initializing script...');

    let contactIndex = 0;
    let educIndex = 0;
    let workExpIndex = 0;

    contactIndex = parseInt(window.contactIndex || 0);
    educIndex = parseInt(window.educIndex || 0);
    workExpIndex = parseInt(window.workExpIndex || 0);

    console.log('Initial indices:', { contactIndex, educIndex, workExpIndex });

    const contactContainer = document.getElementById('contactContainer');
    const educContainer = document.getElementById('educContainer');
    const workExpContainer = document.getElementById('workExpContainer');
    const addContactButton = document.getElementById('addContact');
    const addEducButton = document.getElementById('addEduc');
    const addWorkExpButton = document.getElementById('addWorkExp');
    const resumeForm = document.getElementById('resumeForm');

    // Проверка элементов
    console.log('Elements found:', {
        contactContainer: !!contactContainer,
        educContainer: !!educContainer,
        workExpContainer: !!workExpContainer,
        addContactButton: !!addContactButton,
        addEducButton: !!addEducButton,
        addWorkExpButton: !!addWorkExpButton,
        resumeForm: !!resumeForm
    });

    function createContactForm(index) {
        console.log('Creating contact form for index:', index);
        const formHtml = `
            <div class="contact mb-3 border p-3" data-index="${index}">
                <h3>${messages.contact}</h3>
                <div class="mb-3">
                    <label for="contacts${index}TypeId" class="form-label">${messages.contactType}</label>
                    <select class="form-select" id="contacts${index}TypeId" name="contacts[${index}].typeId">
                        <option value="">${messages.selectContactType}</option>
                        ${contactTypes.map(type => `
                            <option value="${type.id}">${type.type}</option>
                        `).join('')}
                    </select>
                    <span class="error text-danger" id="contacts${index}TypeIdError"></span>
                </div>
                <div class="mb-3">
                    <label for="contacts${index}ContactValue" class="form-label">${messages.link}</label>
                    <input type="text" class="form-control" id="contacts${index}ContactValue" name="contacts[${index}].contactValue">
                    <span class="error text-danger" id="contacts${index}ContactValueError"></span>
                </div>
                <button type="button" class="btn btn-danger removeContact">${messages.remove}</button>
            </div>
        `;
        console.log('Generated HTML for contact form:', formHtml);
        return formHtml;
    }

    function createEducationForm(index) {
        console.log('Creating education form for index:', index);
        const formHtml = `
            <div class="educ mb-3 border p-3" data-index="${index}">
                <h3>${messages.education}</h3>
                <div class="mb-3">
                    <label for="educations${index}Institution" class="form-label">${messages.institution}</label>
                    <input type="text" class="form-control" id="educations${index}Institution" name="educations[${index}].institution">
                    <span class="error text-danger" id="educations${index}InstitutionError"></span>
                </div>
                <div class="mb-3">
                    <label for="educations${index}Program" class="form-label">${messages.program}</label>
                    <input type="text" class="form-control" id="educations${index}Program" name="educations[${index}].program">
                    <span class="error text-danger" id="educations${index}ProgramError"></span>
                </div>
                <div class="mb-3">
                    <label for="educations${index}StartDate" class="form-label">${messages.startDate}</label>
                    <input type="date" class="form-control" id="educations${index}StartDate" name="educations[${index}].startDate">
                    <span class="error text-danger" id="educations${index}StartDateError"></span>
                </div>
                <div class="mb-3">
                    <label for="educations${index}EndDate" class="form-label">${messages.endDate}</label>
                    <input type="date" class="form-control" id="educations${index}EndDate" name="educations[${index}].endDate">
                    <span class="error text-danger" id="educations${index}EndDateError"></span>
                </div>
                <div class="mb-3">
                    <label for="educations${index}Degree" class="form-label">${messages.degree}</label>
                    <input type="text" class="form-control" id="educations${index}Degree" name="educations[${index}].degree">
                    <span class="error text-danger" id="educations${index}DegreeError"></span>
                </div>
                <button type="button" class="btn btn-danger removeEduc">${messages.remove}</button>
            </div>
        `;
        console.log('Generated HTML for education form:', formHtml);
        return formHtml;
    }

    function createWorkExpForm(index) {
        console.log('Creating work experience form for index:', index);
        const formHtml = `
            <div class="workExp mb-3 border p-3" data-index="${index}">
                <h3>${messages.workExperience}</h3>
                <div class="mb-3">
                    <label for="workExperiences${index}Years" class="form-label">${messages.yearsWorked}</label>
                    <input type="number" class="form-control" id="workExperiences${index}Years" name="workExperiences[${index}].years">
                    <span class="error text-danger" id="workExperiences${index}YearsError"></span>
                </div>
                <div class="mb-3">
                    <label for="workExperiences${index}CompanyName" class="form-label">${messages.companyName}</label>
                    <input type="text" class="form-control" id="workExperiences${index}CompanyName" name="workExperiences[${index}].companyName">
                    <span class="error text-danger" id="workExperiences${index}CompanyNameError"></span>
                </div>
                <div class="mb-3">
                    <label for="workExperiences${index}Position" class="form-label">${messages.position}</label>
                    <input type="text" class="form-control" id="workExperiences${index}Position" name="workExperiences[${index}].position">
                    <span class="error text-danger" id="workExperiences${index}PositionError"></span>
                </div>
                <div class="mb-3">
                    <label for="workExperiences${index}Responsibilities" class="form-label">${messages.responsibilities}</label>
                    <textarea class="form-control" id="workExperiences${index}Responsibilities" name="workExperiences[${index}].responsibilities"></textarea>
                    <span class="error text-danger" id="workExperiences${index}ResponsibilitiesError"></span>
                </div>
                <button type="button" class="btn btn-danger removeWorkExp">${messages.remove}</button>
            </div>
        `;
        console.log('Generated HTML for work experience form:', formHtml);
        return formHtml;
    }

    if (!addWorkExpButton) {
        console.error('addWorkExpButton is null. Check if the element with id="addWorkExp" exists in the DOM.');
    }

    addContactButton?.addEventListener('click', () => {
        console.log('Add Contact button clicked, current index:', contactIndex);
        if (!contactContainer) {
            console.error('contactContainer is null. Cannot add contact form.');
            return;
        }
        contactContainer.insertAdjacentHTML('beforeend', createContactForm(contactIndex));
        contactIndex++;
    });

    addEducButton?.addEventListener('click', () => {
        console.log('Add Education button clicked, current index:', educIndex);
        if (!educContainer) {
            console.error('educContainer is null. Cannot add education form.');
            return;
        }
        educContainer.insertAdjacentHTML('beforeend', createEducationForm(educIndex));
        educIndex++;
    });

    addWorkExpButton?.addEventListener('click', () => {
        console.log('Add Work Experience button clicked, current index:', workExpIndex);
        if (!workExpContainer) {
            console.error('workExpContainer is null. Cannot add work experience form.');
            return;
        }
        workExpContainer.insertAdjacentHTML('beforeend', createWorkExpForm(workExpIndex));
        workExpIndex++;
    });

    contactContainer?.addEventListener('click', (event) => {
        if (event.target.classList.contains('removeContact')) {
            event.target.closest('.contact').remove();
        }
    });

    educContainer?.addEventListener('click', (event) => {
        if (event.target.classList.contains('removeEduc')) {
            event.target.closest('.educ').remove();
        }
    });

    workExpContainer?.addEventListener('click', (event) => {
        if (event.target.classList.contains('removeWorkExp')) {
            event.target.closest('.workExp').remove();
        }
    });
});