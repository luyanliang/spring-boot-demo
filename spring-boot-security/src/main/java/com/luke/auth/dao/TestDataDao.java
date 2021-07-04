package com.luke.auth.dao;

import com.luke.auth.bean.ResourceBean;
import com.luke.auth.bean.RoleBean;
import com.luke.auth.bean.UserBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestDataDao {

    private List<UserBean> allUser;

    /**
     * 模拟数据库获取到的数据。
     * admin用户 拥有admin角色，拥有mobile和salary两个资源；
     * mobile用户，拥有mobile角色，拥有mobile资源；
     * worker用户，拥有worker角色，没有资源。
     *
     * @return
     */
    private List<UserBean> getAllUser() {
        if (null == allUser) {
            allUser = new ArrayList<>();

            // 初始化资源权限
            ResourceBean mobileResource = new ResourceBean("1", "mobileRole");
            ResourceBean salaryResource = new ResourceBean("2", "salaryRole");
            List<ResourceBean> adminResources = new ArrayList<>();
            adminResources.add(mobileResource);
            adminResources.add(salaryResource);
            List<ResourceBean> managerResources = new ArrayList<>();
            managerResources.add(salaryResource);

            // 初始化用户角色
            RoleBean adminRole = new RoleBean("1", "adminRole");
            RoleBean managerRole = new RoleBean("2", "managerRole");
            adminRole.setResourceBeans(adminResources);
            managerRole.setResourceBeans(managerResources);

            List<RoleBean> adminRoles = new ArrayList<>();
            adminRoles.add(adminRole);
            List<RoleBean> managerRoles = new ArrayList<>();
            managerRoles.add(managerRole);

            UserBean user1 = new UserBean("1", "admin", "admin");
            user1.setUserRoles(adminRoles);
            user1.setResourceBeans(adminResources);
            UserBean user2 = new UserBean("2", "manager", "manager");
            user2.setUserRoles(managerRoles);
            user2.setResourceBeans(managerResources);
            UserBean user3 = new UserBean("3", "worker", "worker");

            allUser.add(user1);
            allUser.add(user2);
            allUser.add(user3);
        }
        return allUser;
    }

    public UserBean queryUser(UserBean user) {
        List<UserBean> allUser = this.getAllUser();
        List<UserBean> userList = allUser.stream()
                .filter(i -> i.getUserName().equals(user.getUserName()) && i.getUserPass().equals(user.getUserPass()))
                .collect(Collectors.toList());
        return userList.size() > 0 ? userList.get(0) : null;
    }

    public UserBean queryUser(String userName) {
        List<UserBean> allUser = this.getAllUser();
        List<UserBean> userList = allUser.stream()
                .filter(i -> i.getUserName().equals(userName))
                .collect(Collectors.toList());
        return userList.size() > 0 ? userList.get(0) : null;
    }
}
