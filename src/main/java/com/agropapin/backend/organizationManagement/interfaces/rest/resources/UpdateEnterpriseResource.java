package com.agropapin.backend.organizationManagement.interfaces.rest.resources;

public record UpdateEnterpriseResource(
        String enterpriseName,
        String description,
        String country,
        String ruc,
        String phone,
        String website,
        String profileImgUrl,
        String sector
) {
    public UpdateEnterpriseResource {
        if (enterpriseName == null) {
            System.out.println("enterpriseName is null");
            throw new NullPointerException("enterpriseName is null");
        }
        if (description == null) {
            System.out.println("description is null");
            throw new NullPointerException("description is null");
        }
        if (country == null) {
            System.out.println("country is null");
            throw new NullPointerException("country is null");
        }
        if (ruc == null) {
            System.out.println("ruc is null");
            throw new NullPointerException("ruc is null");
        }
        if (phone == null) {
            System.out.println("phone is null");
            throw new NullPointerException("phone is null");
        }
        if (website == null) {
            System.out.println("website is null");
            throw new NullPointerException("website is null");
        }
    }
}
