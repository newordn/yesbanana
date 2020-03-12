package com.derteuffel.controller;

import com.derteuffel.data.Education;
import com.derteuffel.data.Region;
import com.derteuffel.data.Secondary;
import com.derteuffel.repository.RegionRepository;
import com.derteuffel.repository.SecondaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by derteuffel on 27/12/2018.
 */
@Controller
@RequestMapping("/secondaire")
public class SecondaryController {

    @Autowired
    private SecondaryRepository secondaryRepository;


    @Autowired
    private RegionRepository regionRepository;


}
