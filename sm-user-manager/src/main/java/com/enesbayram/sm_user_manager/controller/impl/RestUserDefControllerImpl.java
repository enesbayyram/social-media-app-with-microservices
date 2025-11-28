package com.enesbayram.sm_user_manager.controller.impl;

import com.enesbayram.sm_core.base.dto.DtoUserDef;
import com.enesbayram.sm_core.base.dto.DtoUserDefIU;
import com.enesbayram.sm_core.base.model.UserDef;
import com.enesbayram.sm_core.base.model.base.RootEntity;
import com.enesbayram.sm_core.base.restservice.base.RestBaseController;
import com.enesbayram.sm_user_manager.controller.IRestUserDefController;
import com.enesbayram.sm_user_manager.service.IUserDefService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class RestUserDefControllerImpl extends RestBaseController implements IRestUserDefController {

    private final IUserDefService userDefService;

    @GetMapping("/username/{username}")
    @Override
    public RootEntity<DtoUserDef> findByUsername(@PathVariable(value = "username", required = true) String username) {
        UserDef userDef = userDefService.findByUsername(username);
        return ok(userDefService.toDTO(userDef));
    }

    @GetMapping(path = "/image/{userDefId}" ,  produces = MediaType.IMAGE_JPEG_VALUE)
    @Override
    public ResponseEntity<byte[]> getUserProfilePhoto(@PathVariable(value = "userDefId" , required = true) String userDefId) {
        UserDef userDef = userDefService.findByUserId(userDefId);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(userDef.getProfilePhoto());
    }

    @GetMapping("/userDefId/{userDefId}")
    @Override
    public RootEntity<UserDef> findByUserId(@PathVariable(value = "userDefId", required = true) String userDefId) {
        return ok(userDefService.findByUserId(userDefId));
    }

    @PostMapping(path = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public RootEntity<DtoUserDef> saveUserDef(@RequestPart(value = "user") @Valid DtoUserDefIU dtoUserDefIU,
                                              @RequestPart(value = "profilePhoto", required = false) MultipartFile profilePhoto) throws IOException {
        return ok(userDefService.toDTO(userDefService.saveUserDef(dtoUserDefIU, profilePhoto)));
    }

    @PutMapping("/update/{userId}")
    @Override
    public RootEntity<DtoUserDef> updateUserDef(@PathVariable(name = "userId", required = true) String userId,
                                                @RequestBody @Valid DtoUserDefIU dtoUserDefIU) {
        return ok(userDefService.toDTO(userDefService.updateUserDef(userId, dtoUserDefIU)));
    }

    @DeleteMapping("/delete/{username}")
    @Override
    public RootEntity<Boolean> deleteUser(@PathVariable(value = "username", required = true) String username) {
        return ok(userDefService.deleteUser(username));
    }
}
