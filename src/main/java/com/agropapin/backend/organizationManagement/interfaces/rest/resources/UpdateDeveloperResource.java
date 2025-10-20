package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

public record UpdateDeveloperResource(
        String firstName,
        String lastName,
        String description,
        String country,
        String phone,
        String specialties,
        String profileImgUrl
) {
    public UpdateDeveloperResource {
        if (firstName == null) {
            System.out.println("firstName is null");
            throw new NullPointerException("firstName is null");
        }
        if (lastName == null) {
            System.out.println("lastName is null");
            throw new NullPointerException("lastName is null");
        }
        if (description == null) {
            System.out.println("description is null");
            throw new NullPointerException("description is null");
        }
        if (country == null) {
            System.out.println("country is null");
            throw new NullPointerException("country is null");
        }
        if (phone == null) {
            System.out.println("phone is null");
            throw new NullPointerException("phone is null");
        }
        if (specialties == null) {
            System.out.println("specialties is null");
            throw new NullPointerException("specialties is null");
        }
    }
}
