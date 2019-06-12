package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.ImageService;
import ar.edu.itba.paw.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {

    @Autowired
    private ImageService imageService;

    @RequestMapping(value = "images/{id}", method = RequestMethod.GET)
    public @ResponseBody byte[]
    get(@PathVariable long id) {
        Image im = imageService.get(id);
        byte[] image = imageService.get(id).getImage();
        if(image != null)
            return image;
        else
            return new byte[] { };
    }
}
