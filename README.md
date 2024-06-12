
# Recipe Finder App

Recipe Finder App is an Android application designed to provide users with a wide variety of recipes across different categories. The app allows users to browse, view detailed information, and save their favorite recipes for offline access. Additionally, it includes user authentication, an AI-powered question-answering feature, and periodic recipe updates with notifications.


## Features

- **User Authentication:** Users can sign up and log in using Firebase Authentication.
- **Recipe Browsing:** Users can browse recipes across various categories.
- **Recipe Details:** Users can view detailed information about recipes, including ingredients, instructions, and similar recipes.
- **Favorites:** Users can save their favorite recipes for offline access using Room Database.
- **AI-Powered Q&A:** Users can ask questions related to recipes and get answers powered by Gemini AI.
- **Periodic Updates:** The app periodically fetches new recipes in the background using Work Manager and notifies users.
- **Modern UI:** Built with Jetpack Compose for a dynamic and interactive user interface.
- **Offline Access:** Favorite recipes are accessible without an internet connection.
- **Notifications:** Users receive notifications about new recipes.

  
## Technologies Used

- **Kotlin:** Core programming language.
- **Jetpack Compose:** For building the user interface.
- **Retrofit:** For networking and API calls.
- **Coil:** For image loading.
- **Gson:** For JSON processing.
- **Room:** For local database storage.
- **Coroutines:** For asynchronous programming.
- **Work Manager:** For background tasks.
- **Firebase:** For user authentication.
- **Gemini AI:** For the AI-powered question-answering feature.
  
## Getting Started
### Prerequisites
- Android Studio 4.0 or later.
- A Firebase project for authentication.
- API keys for the recipe API.
- Gemini AI API access for the Q&A feature.
## Installation 

1. Clone the repository:

```bash 
 git clone https://github.com/uludagmert/RecipeFinderApp.git
```
2. Open the project in Android Studio.
3. Configure Firebase:
- Follow Firebase setup instructions to add your project to Firebase.
- Add the google-services.json file to the app directory.
4. Add API Keys:
- Add your recipe API key and Gemini AI API key to the local.properties file or directly in your code.
5. Build the project:
- Sync Gradle and build the project in Android Studio.

## Running the App
- Connect an Android device or use an emulator.
- Click the "Run" button in Android Studio.
## Contribution

Contributions are welcome! Please follow these steps to contribute:

1. Fork the repository.
2. Create a new branch (git checkout -b feature-branch).
3. Make your changes.
4. Commit your changes (git commit -m 'Add some feature').
5. Push to the branch (git push origin feature-branch).
6. Open a Pull Request.

  
## License

[MIT](https://choosealicense.com/licenses/mit/)

  
## Screenshots

![Uygulama Ekran Görüntüsü 1](https://drive.google.com/file/d/17GAy5Xu9AHQtuggsCvNeSCKROXMCUiWF/view?usp=drive_link)

![Uygulama Ekran Görüntüsü 2](https://drive.google.com/file/d/1uroacoO49G50d6IHZL-tu_nYL3t5Ow8q/view?usp=sharing)

![Uygulama Ekran Görüntüsü 3](https://drive.google.com/file/d/1CzSbFu2fAreYnujirvyGxKnA60XqzZWs/view?usp=sharing)

  
