# InspiringPersons_Fragment_Version
Android application for creating a list of inspiring people

## About
Android project that enables saving, fetching, editing and deleting inspiring people from local database using Room which provides an abstraction layer over SQLite. Glide Image Loader is used for listing people images in RecyclerView. Also SharedPreferences are used to save local dates. This version provides different approach of showing different screens by using ViewPager2 with Fragments and TabLayout.

## Features
The android app lets you:

- Save person that inspires you (image, description, quotes, birth date)
- Review saved inspiring people
- Edit person that inspires you
- Delete persons 
 
## Tech-stack
The project seeks to use recommended practices and libraries in Android development.
- MVVM architecture (Viewmodel + LiveData)
- View Binding
- RecyclerView
- ViewPager2
- Fragments
- SharedPreferences
- Room
- Glide
- Coroutines 
- Hilt dependency injection
- ...

## Screenshots
![image](https://user-images.githubusercontent.com/75457058/129003438-f340313b-b942-48b0-acb8-61b9397d0bd0.png)
![image](https://user-images.githubusercontent.com/75457058/129003507-d908f6bb-fc5d-401d-aabf-e395eeac0c16.png)

## Setup
1. Clone the repository
```
https://github.com/kovaccc/InspiringPersons_Fragment_Version.git
```
2. Open the project with your IDE/Code Editor
3. Run it on simulator or real Android device
