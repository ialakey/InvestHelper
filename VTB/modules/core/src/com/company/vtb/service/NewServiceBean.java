package com.company.vtb.service;

import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.PasswordEncryption;
import com.haulmont.cuba.security.entity.Group;
import com.haulmont.cuba.security.entity.User;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(NewService.NAME)
public class NewServiceBean implements NewService {

    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;
    @Inject
    private PasswordEncryption passwordEncryption;

    @Override
    public void registrationUser(String login, String password) {
        User user = metadata.create(User.class);
        user.setLogin(login);
        user.setPassword(passwordEncryption.getPasswordHash(user.getId(), password));

        com.haulmont.cuba.security.entity.UserRole userRole = metadata.create(com.haulmont.cuba.security.entity.UserRole.class);
        userRole.setUser(user);
        userRole.setRoleName("rest-api-access");
        Group group = dataManager.load(Group.class).one();
        user.setGroup(group);
        user.setLanguage("en");

        dataManager.commit(user, userRole);
    }
}