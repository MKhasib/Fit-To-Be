package com.example.fittobe.Models;


import java.util.List;

public class User {
    private String fullName;
    private String email;
    private String password;
    private int age;
    private double weight;
    private String gender;
    private List<Exercise> favoriteExercises;

    public User(String fullName, String email, String password, int age, double weight, String gender) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Exercise> getFavoriteExercises() {
        return favoriteExercises;
    }

    public void setFavoriteExercises(List<Exercise> favoriteExercises) {
        this.favoriteExercises = favoriteExercises;
    }

    @Override
    public String toString() {
        return "User{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", weight=" + weight +
                ", gender='" + gender + '\'' +
                ", favoriteExercises=" + favoriteExercises +
                '}';
    }
}