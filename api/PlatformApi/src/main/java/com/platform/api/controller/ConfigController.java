package com.platform.api.controller;


/**
 * Created by Alexander Kozlov <sasha.lamaka@gmail.com> on 1/17/17.
 */
@RestController
@RequestMapping("/api/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;


    @RequestMapping(method = RequestMethod.GET)
    public ConfigDto getConfig()
    {
        ...
    }

}
