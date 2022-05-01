package com.chensy.socialnetwork.service.impl;

import com.chensy.socialnetwork.converters.UserDtoToUserConverter;
import com.chensy.socialnetwork.converters.UserToUserDtoConverter;
import com.chensy.socialnetwork.dao.FriendshipDao;
import com.chensy.socialnetwork.dto.UserDTO;
import com.chensy.socialnetwork.model.Friendship;
import com.chensy.socialnetwork.model.User;
import com.chensy.socialnetwork.service.FriendsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class FriendsServiceImpl implements FriendsService {

    private final FriendshipDao friendshipDao;
    private final UserToUserDtoConverter userToUserDtoConverter;
    private final UserDtoToUserConverter userDtoToUserConverter;

    public FriendsServiceImpl(FriendshipDao friendshipDao,
                              UserToUserDtoConverter userToUserDtoConverter,
                              UserDtoToUserConverter userDtoToUserConverter) {
        this.friendshipDao = friendshipDao;
        this.userToUserDtoConverter = userToUserDtoConverter;
        this.userDtoToUserConverter = userDtoToUserConverter;
    }

    @Override
    public Map<String, Set<UserDTO>> getFriends(Long userId, String search) {
        List<Friendship> requests = friendshipDao.findAllByUserSenderIdOrUserReceiverId(userId, userId);
        Map<Boolean, List<Friendship>> requestMap = requests.stream()
                .collect(Collectors.partitioningBy(Friendship::getAccepted));

        Predicate<User> userPredicate = u -> StringUtils.isEmpty(search) ||
                (u.getFirstName() + ' ' + u.getLastName()).toLowerCase().contains(search.toLowerCase());
        Set<UserDTO> usersNotAcceptedRequests = requestMap.get(false).stream()
                .filter(r -> r.getUserSender().getId().equals(userId))
                .map(Friendship::getUserReceiver)
                .filter(userPredicate)
                .map(userToUserDtoConverter::convert)
                .collect(Collectors.toSet());

        Set<UserDTO> notAcceptedRequestsToUser = requestMap.get(false).stream()
                .filter(r -> r.getUserReceiver().getId().equals(userId))
                .map(Friendship::getUserSender)
                .filter(userPredicate)
                .map(userToUserDtoConverter::convert)
                .collect(Collectors.toSet());

        Set<UserDTO> friendsOfUser = requestMap.get(true).stream()
                .map(r -> r.getUserSender().getId().equals(userId) ? r.getUserReceiver() : r.getUserSender())
                .filter(userPredicate)
                .map(userToUserDtoConverter::convert)
                .collect(Collectors.toSet());

        Map<String, Set<UserDTO>> map = new HashMap<>();
        map.put("usersNotAcceptedRequests", usersNotAcceptedRequests);
        map.put("notAcceptedRequestsToUser", notAcceptedRequestsToUser);
        map.put("friendsOfUser", friendsOfUser);

        return map;
    }

    @Override
    public Set<UserDTO> getAcceptedFriendshipUsers(Long id) {
        List<Friendship> friends = friendshipDao.findAcceptedFriendshipUsers(id);
        return friends.stream()
                      .map(r -> r.getUserSender().getId().equals(id) ? r.getUserReceiver() : r.getUserSender())
                      .map(userToUserDtoConverter::convert)
                      .collect(Collectors.toSet());
    }

    @Override
    public void deleteFriendship(UserDTO userDTO, Long friendId) {
        User user = userDtoToUserConverter.convert(userDTO);
        friendshipDao.deleteFriendRequests(user, friendId);
    }

    @Override
    @Transactional
    public void acceptFriendship(UserDTO userDTO, Long friendId) {
        User user = userDtoToUserConverter.convert(userDTO);
        friendshipDao.deleteFriendRequests(user, friendId);
        friendshipDao.addFriendship(user, friendId);
    }

    @Override
    public void addToFriends(UserDTO userDTO, Long friendId) {
        User user = userDtoToUserConverter.convert(userDTO);
        friendshipDao.addToFriends(user, friendId);
    }

    @Override
    public Boolean checkFriendship(UserDTO userDTO, UserDTO friendDTO) {
        User user = userDtoToUserConverter.convert(userDTO);
        User friend = userDtoToUserConverter.convert(friendDTO);
        return friendshipDao.checkFriendshipExists(user, friend);
    }
}
