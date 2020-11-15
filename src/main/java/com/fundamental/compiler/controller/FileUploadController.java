package com.fundamental.compiler.controller;

import com.fundamental.compiler.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("upload")
@SessionAttributes({"yours", "isEqualOrNot"})
public class FileUploadController {
    @Autowired
    StorageService storageService;

    @GetMapping
    public String uploadFile(Model model, HttpSession httpSession) {
        List files = storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toUri().toString())
                .collect(Collectors.toList());
        model.addAttribute("files", files);
        String filename = "";
        for (Object file : files) {
            String element = file.toString();
            if (element.endsWith(".c")) {
                filename = element;
            }
        }
//        static result (connect database to make it dynamic)
        int result = 12;
        model.addAttribute("result", result);
//        recur this page until we get result of uploaded file
        if (!filename.isEmpty()) {
            int yours = storageService.readRs(filename);
            if (yours != 0) {
                httpSession.setAttribute("yours", yours);
                if (yours == result) {
                    httpSession.setAttribute("isEqualOrNot", true);
                }
                storageService.deleteAll();
                storageService.init();
            }
            return "redirect:/upload";
        }
//        finally
        model.addAttribute("yours", httpSession.getAttribute("yours"));
        model.addAttribute("isEqualOrNot", httpSession.getAttribute("isEqualOrNot"));
        return "uploadForm";
    }

    @PostMapping("files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/upload";
    }
}
