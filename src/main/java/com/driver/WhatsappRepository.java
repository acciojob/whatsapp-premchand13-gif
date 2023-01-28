package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }
    public String createUser(String name, String mobile){
        if(userMobile.contains(mobile)){
            return null;
        }
        userMobile.add(mobile);
        return "SUCCESS";
    }
    public Group createGroup(List<User> users){
        int c=users.size();
        if(c==2){
            Group group=new Group(users.get(1).getName(),c);
            adminMap.put(group,users.get(0));
            groupUserMap.put(group,users);
            return group;
        }
        else if(c>2){
            customGroupCount++;
            Group group=new Group("Group "+customGroupCount,c);
            adminMap.put(group,users.get(0));
            groupUserMap.put(group,users);
            return group;
        }
        return null;
    }
    //3
    public int createMessage(String content){
        messageId++;
        Message message=new Message(messageId,content,new Date());
        return messageId;

    }
    //4
    public int sendMessage(Message message, User sender, Group group){
        if(!groupUserMap.containsKey(group)){
            return -1;
        }
        List<User> lst=groupUserMap.get(group);
        if(!lst.contains(sender)){
            return -2;
        }
        if(groupMessageMap.containsKey(group)){
            List<Message> lt=groupMessageMap.get(group);
            lt.add(message);
            groupMessageMap.put(group,lt);
        }
        else{
            List<Message> lt=new ArrayList<>();
            lt.add(message);
            groupMessageMap.put(group,lt);
        }
        return groupMessageMap.get(group).size();
    }

    //5
    public String changeAdmin(User approver, User user, Group group){
        if(!groupUserMap.containsKey(group)){
            return "1";
        }
        List<User> lst=groupUserMap.get(group);

        User admin=adminMap.get(group);
        if(!admin.equals(approver)){
            return "2";
        }
        if(!lst.contains(user)){
            return "3";
        }
        adminMap.put(group,user);
        return "SUCCESS";
    }
























}
