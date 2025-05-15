package com.soft.recipservice.service;

import com.flickr4java.flickr.FlickrException;

import java.io.InputStream;

public interface FlickrService {

    public String savePhoto(InputStream photo, String title) throws FlickrException;
}
