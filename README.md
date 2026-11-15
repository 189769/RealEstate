# Real Estate Tenant Management System

## 1. Project Overview

The Real Estate Tenant Management System is an Android application developed to help property managers organize tenant information and monitor monthly rental amounts.

The application provides a simple interface for registering users, adding tenants, viewing tenant records, and monitoring rental information through a dashboard.

This project was developed using Kotlin and Android Studio as a group academic project.

## 2. Implemented Features

- **User Registration:** Allows users to create a local account.
- **User Login:** Validates registered user credentials.
- **Dashboard:** Displays the number of tenants and total monthly rent.
- **Add Tenant:** Allows users to register tenants with their names, phone numbers, and monthly rent.
- **View Tenants:** Displays saved tenant records.
- **Phone Dialer:** Opens the Android dialer with a selected tenant's phone number.
- **Database Persistence:** Uses Room Database to preserve tenant records after the application restarts.

## 3. Technologies Used

- Kotlin
- Android Studio
- XML layouts
- Room Database
- SQLite
- MVVM architecture
- ViewModel and LiveData
- Kotlin Coroutines
- Git and GitHub

## 4. Project Structure

```text
app/src/main/java/com/example/tenantmanagementsystem/
├── data/
│   ├── local/
│   ├── model/
│   └── repository/
└── ui/
    ├── auth/
    ├── dashboard/
    ├── main/
    ├── tenants/
    └── viewmodel/
```

## 5. How to Run the Application

1. Clone the GitHub repository.
2. Open the project in Android Studio.
3. Allow Gradle Sync to complete.
4. Start an Android emulator or connect a physical Android device.
5. Build and run the application.
6. Register a test account and log in.
7. Use the dashboard to add and view tenant records.

## 6. Testing

The following functionality was tested successfully:

- User registration
- Login with registered credentials
- Incorrect password rejection
- Dashboard navigation
- Tenant registration
- Tenant count updates
- Monthly rent calculation
- Viewing saved tenants
- Phone dialer integration
- Tenant record persistence after application restart

## 7. Group Contributions

The Real Estate Tenant Management System was developed collaboratively by six group members from Strathmore University.

| Admission Number | Full Name | Role and Contribution |
|---|---|---|
| 189769 | John Wambugu | **Group Leader — Stage 1:** Initial Android project setup, application navigation, authentication screens, and tenant management foundation. |
| 184684 | Nicholas Rogena | **Stage 2 — ViewModel:** Implemented TenantViewModel and LiveData. |
| 193557 | Collins Karani | **Stage 3 — Room Database:** Implemented the Room database, Tenant entity, and DAO. |
| 190066 | Timothy Kasaine | **Stage 4 — UI Integration:** Connected tenant screens and the dashboard to the ViewModel. |
| 189628 | Sammy Kiema | **Stage 5 — Data Integration:** Integrated the Room database with the repository and ViewModel. |
| 189740 | Titus Gakunju | **Stage 6 — Testing and Release:** Authentication improvements, application testing, documentation, and final project preparation. |

### Collaboration

The group used Git and GitHub for version control, feature branches, pull requests, and collaborative development.
## 8. Known Limitations

- Authentication is implemented locally for demonstration purposes and is not suitable for production use.
- The local registration implementation supports one stored account at a time.
- Tenant editing and deletion are not yet available.
- Property management and rent payment tracking are not yet implemented.
- Tenant data is stored locally on the device rather than synchronized across multiple devices.

## 9. Future Improvements

- Add tenant editing and deletion.
- Implement property and unit management.
- Add rent payment recording and payment history.
- Introduce secure multi-user authentication.
- Add cloud synchronization and backup.
- Generate rental reports.

## 10. Conclusion

The Real Estate Tenant Management System demonstrates Android application development using Kotlin, Room Database, and MVVM architecture.

The application provides essential tenant management functionality and can be expanded to support more advanced real estate operations.

## Group Members and Contributions

| No. | Full Name         | Admission Number | Responsibility |
|---|-------------------|------------------|---|
| 1 | [john wambugu]    | [189769]         | Stage 1 — Project Lead and Architecture |
| 2 | [Nicholas Rogena] | [184684]         | Stage 2 — ViewModel and LiveData |
| 3 | [Collins Karani]  | [193557]         | Stage 3 — Room Database |
| 4 | [Timothy Kasaine] | [190066]         | Stage 4 — UI and ViewModel Integration |
| 5 | [Sammy Kiema]     | [189628]         | Stage 5 — Data Integration |
| 6 | [Titus Gakunji]   | [189740]         | Stage 6 — Testing, Documentation and Release |

## GitHub Collaboration

The project was developed collaboratively using Git feature branches, pull requests, code reviews and merges.

All project contributions can be viewed under the repository's GitHub commit history and pull requests.