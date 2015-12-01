# DataDude

## Overview

Data management is one of the most important functions of a mobile app. Unfortunately, there are tons of ways to execute it. My goal with this is to demonstrate some methodologies that increase efficiency and data safety throughout diverse kinds of projects.

## Goal

I want to use this repository to accompany my explorations of best practices in data management, from my blog series, ["Dude, Where's My Data"](http://joe.azandria.com/posts/8).

This project will morph and evolve as I update that series of posts, and I'm hoping to end up with a pretty sweet little library (or at least some good boilerplate that I can share in the Android community) that offers a clean, clear, and comprehensive grasp on Android app data management.

I will try to update this README as I go along, but I make no promises.

## Structure

The repository is currently a free-form Android project built on gradle. The description here applies as of commit [4232639](https://github.com/onemahon/DataDude/commit/42326390d4171a21dccbeab17141bf20fa7547cd).

The main package, `com.azandria.datadude`, cedes into two directories, `data` and `examples`. `data` is the core of the project - that's where the classes that I'm writing that are meant to be static boilerplate code will live. The other directory, `examples`, is where all the sample code I write for that series of posts will stay.

Currently, the app has four different activities, each of which you can test out by changing the main Activity in the manifest file.

* **SimpleRequestActivity**: get started with a look at the function of `DataRequestBuilder`, `IDataRequestMethod`, and `BasicDataRequestResponse`. Accompanying [blog post](http://joe.azandria.com/posts/9).
* **LoadingStatesActivity**: take a peek at how to deal with multiple states of one view. Notably, "content", "error", "loading", and "empty".
* **ListWithLoadingStatesActivity**: Integrate the information from the LoadingStatesActivity into a list of objects. Also, customizing particular states for single implementations (e.g. a custom "empty" screen). Accompanying [blog post](http://joe.azandria.com/posts/11).
* **ListFromApiActivity**: Learn how to gather information from the *Real Live Web* to display according to the principles we've covered so far. Accompanying [blog post](http://joe.azandria.com/posts/13).
