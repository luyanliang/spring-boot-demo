package com.luke.camunda.identity;

import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * 用户和用户组组件测试类
 * <p>
 * 1. 添加用户或者用户组时，默认的ID格式必须是数字或者camunda-admin。
 * 参考{@link org.camunda.bpm.engine.ProcessEngineConfiguration#setGroupResourceWhitelistPattern(String)}}.
 * 2. 在新增用户、组、租户前，会校验该对象的REV_属性值，如果值为0表示新增，否则进行修改。
 *
 * @author LuYanLiang [765673481@qq.com]
 * @since 2020/11/10 18:34
 */
public class IdentityServiceTest {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    private IdentityService identityService = processEngine.getIdentityService();

    /**
     * 新增用户
     * <p>
     * insert into ACT_ID_USER (ID_, FIRST_, LAST_, EMAIL_, PWD_, SALT_, REV_) values ( ?, ?, ?, ?, ?, ?, 1 )
     */
    @Test
    public void saveUserTest() {
        User user = identityService.newUser("1");
        user.setEmail("test@qq.com");
        user.setFirstName("lu");
        user.setPassword("test");
        identityService.saveUser(user);
    }

    /**
     * 删除用户
     * <p>
     * delete from ACT_ID_USER where ID_ = ? and REV_ = ?
     */
    @Test
    public void deleteUserTest() {
        identityService.deleteUser("1");
    }

    /**
     * 使用原生SQL查询用户列表
     */
    @Test
    public void createNativeSqlTest() {
        String sql = "SELECT * FROM ACT_ID_USER;";
        List<User> userList = identityService.createNativeUserQuery().sql(sql).list();
        System.out.println("查询用户数量为：" + userList.size());
    }

    /**
     * 校验用户密码
     */
    @Test
    public void checkUserPwdTest() {
        boolean result = identityService.checkPassword("1", "test");
        Assert.assertTrue(result);
    }

    /**
     * 添加用户组
     * <p>
     * insert into ACT_ID_GROUP (ID_, NAME_, TYPE_, REV_) values ( ?, ?, ?, 1 )
     */
    @Test
    public void saveGroupTest() {
        Group group = identityService.newGroup("1");
        group.setName("IT开发部");
        group.setType("manager");
        identityService.saveGroup(group);
    }

    /**
     * 添加或修改用户组
     * 在调用saveGroup方法前，会检验Group的REV_属性值，如果该值为0，表示新增；否则就是修改。
     * <p>
     * update ACT_ID_GROUP set REV_ = ?, NAME_ = ?, TYPE_ = ? where ID_ = ? and REV_ = ?
     */
    @Test
    public void saveOrUpdateGroupTest() {
        Group group = identityService.createGroupQuery().groupId("1").singleResult();
        group.setName("IT开发部1");
        group.setType("manager");
        identityService.saveGroup(group);
    }

    /**
     * 查询用户组
     * <p>
     * select distinct RES.* from ACT_ID_GROUP RES WHERE RES.ID_ = ? order by RES.ID_ asc LIMIT ? OFFSET ?
     */
    @Test
    public void queryGroupTest() {
        Group group = identityService.createGroupQuery().groupId("1").singleResult();
        System.out.println(group.toString());
    }

    /**
     * 删除用户组
     * <p>
     * delete from ACT_ID_MEMBERSHIP where GROUP_ID_ = ?
     */
    @Test
    public void deleteGroupTest() {
        identityService.deleteGroup("1");
    }

    /**
     * 绑定用户与用户组的关联关系
     * <p>
     * insert into ACT_ID_MEMBERSHIP (USER_ID_, GROUP_ID_) values ( ?, ? )
     */
    @Test
    public void createMembershipTest() {
        String userId = "1";
        String groupId = "1";
        identityService.createMembership(userId, groupId);
    }

    /**
     * 解绑用户与用户组的关联关系
     * <p>
     * delete from ACT_ID_MEMBERSHIP where USER_ID_ = ? and GROUP_ID_ = ?
     */
    @Test
    public void deleteMembershipTest() {
        String userId = "1";
        String groupId = "1";
        identityService.deleteMembership(userId, groupId);
    }
}
