document.addEventListener('DOMContentLoaded', () => {
    // Обработка обновления резюме
    const resumeRefreshButtons = document.querySelectorAll('.refreshButton');
    resumeRefreshButtons.forEach(button => {
        button.addEventListener('click', async () => {
            const resumeId = button.getAttribute('data-resume-id');
            if (!resumeId) {
                console.error('resumeId is missing or null');
                alert('Ошибка: ID резюме не найден');
                return;
            }

            const updatedTimeElement = document.querySelector(`.updated-time[data-resume-id="${resumeId}"]`);
            if (!updatedTimeElement) {
                console.error(`No updated-time element found for resumeId ${resumeId}`);
                alert('Ошибка: элемент даты не найден');
                return;
            }

            try {
                const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
                const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

                const headers = {
                    'Content-Type': 'application/x-www-form-urlencoded'
                };
                if (csrfToken && csrfHeader) {
                    headers[csrfHeader] = csrfToken;
                }

                const response = await fetch('/resumes/refresh', {
                    method: 'POST',
                    headers: headers,
                    body: `resumeId=${encodeURIComponent(resumeId)}&redirectURI=${encodeURIComponent('/users/profile')}`
                });

                if (response.ok) {
                    const now = new Date();
                    const formattedDate = now.toLocaleDateString('ru-RU', {
                        day: '2-digit',
                        month: '2-digit',
                        year: 'numeric'
                    });
                    updatedTimeElement.textContent = formattedDate;
                    button.disabled = true;
                    setTimeout(() => button.disabled = false, 1000);
                } else {
                    const errorText = await response.text();
                    console.error('Server error (resume):', errorText);
                    alert('Ошибка при обновлении резюме: ' + errorText);
                }
            } catch (error) {
                console.error('Fetch error (resume):', error);
                alert('Произошла ошибка при обновлении резюме: ' + error.message);
            }
        });
    });

//вакансии
    const vacancyRefreshButtons = document.querySelectorAll('.refreshVacancyButton');
    vacancyRefreshButtons.forEach(button => {
        button.addEventListener('click', async () => {
            const vacancyId = button.getAttribute('data-vacancy-id');
            if (!vacancyId) {
                console.error('vacancyId is missing or null');
                alert('Ошибка: ID вакансии не найден');
                return;
            }

            const updatedTimeElement = document.querySelector(`.updated-time[data-vacancy-id="${vacancyId}"]`);
            if (!updatedTimeElement) {
                console.error(`No updated-time element found for vacancyId ${vacancyId}`);
                alert('Ошибка: элемент даты не найден');
                return;
            }

            try {
                const csrfToken = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
                const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

                const headers = {
                    'Content-Type': 'application/x-www-form-urlencoded'
                };
                if (csrfToken && csrfHeader) {
                    headers[csrfHeader] = csrfToken;
                }

                const response = await fetch('/vacancies/refresh', {
                    method: 'POST',
                    headers: headers,
                    body: `vacancyId=${encodeURIComponent(vacancyId)}&redirectURI=${encodeURIComponent('/users/profile')}`
                });

                if (response.ok) {
                    const now = new Date();
                    const formattedDate = now.toLocaleDateString('ru-RU', {
                        day: '2-digit',
                        month: '2-digit',
                        year: 'numeric'
                    });
                    updatedTimeElement.textContent = formattedDate;
                    button.disabled = true;
                    setTimeout(() => button.disabled = false, 1000);
                } else {
                    const errorText = await response.text();
                    console.error('Server error (vacancy):', errorText);
                    alert('Ошибка при обновлении вакансии: ' + errorText);
                }
            } catch (error) {
                console.error('Fetch error (vacancy):', error);
                alert('Произошла ошибка при обновлении вакансии: ' + error.message);
            }
        });
    });
});