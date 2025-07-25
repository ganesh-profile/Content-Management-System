package com.CMS.Content.Management.System.security.service.serviceImp;

import com.CMS.Content.Management.System.security.repository.accountCreationRepository;
import com.CMS.Content.Management.System.security.service.accountCreationService;
import com.CMS.Content.Management.System.security.user.userDetailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCreateServiceImp implements accountCreationService {

    private final accountCreationRepository accountCreationRepository;

    @Override
    public int accountCreationProcess(userDetailModel userDetailModel) {
        userDetailModel.setPassword(new BCryptPasswordEncoder().encode(userDetailModel.getPassword()));
        userDetailModel.setEnabled(true);

        int userAccountCreationOutput = accountCreationRepository.createUserAccount(userDetailModel);
        int adminAccountCreationOutput = accountCreationRepository.createAdminAccount(userDetailModel);

        int BouncedTogetherResult = userAccountCreationOutput + adminAccountCreationOutput;

        if (BouncedTogetherResult > 1) {
            return BouncedTogetherResult;
        } else {
            return -1;
        }
    }
}
