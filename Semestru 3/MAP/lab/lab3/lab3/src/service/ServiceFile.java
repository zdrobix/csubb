package service;

import repo.file.PrietenieFile;
import repo.file.UtilizatorFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceFile {
    /*
                Clasa pentru testarea functionalitatilor de Community analysis.

                Va fi stearsa in momentul in care nu va mai fi folosit de loc file repo.

                /////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////
                /////////////////////////////////////////////////////////////////////

                Acest service contine doar functionalitatile legade de comunitati

     */
    private final UtilizatorFile repo;
    private final PrietenieFile repoPrieteni;

    public ServiceFile(UtilizatorFile repo_, PrietenieFile repoPrieteni_) {
        this.repo = repo_;
        this.repoPrieteni = repoPrieteni_;
    }

    private Integer DFS(Long userId, Set<Long> visited, List<Long> userIds)  {
        visited.add(userId);
        int size = 1;
        userIds.add(userId);
        var list_friends = new ArrayList<Long>();
        for (var friendship : this.repoPrieteni.findAll()) {
            if (friendship.getIdFriend1() == userId)
                list_friends.add(friendship.getIdFriend2());
            if (friendship.getIdFriend2() == userId)
                list_friends.add(friendship.getIdFriend1());
        }
        for (Long friendId : list_friends) {
            if (!visited.contains(friendId)) {
                size += DFS(friendId, visited, userIds);
            }
        }
        return size;

    }

    public Integer numberOfCommunities()  {
        Set<Long> visited = new HashSet<Long>();
        int count = 0;
        List<Long> list = new ArrayList<>();
        for (var user : this.repo.findAll()) {
            Long userId = user.getId();
            if (!visited.contains(userId)) {
                DFS(userId, visited, list);
                count++;
            }
        }
        return count;
    }

    public List<Long> largestCommunity ()  {
        Set<Long> visited = new HashSet<Long>();
        List<Long> largestCommunityIds = new ArrayList<>();
        int maxSize = 0;
        for (var user : this.repo.findAll()) {
            Long userId = user.getId();
            if (!visited.contains(userId)) {
                List<Long> userIds = new ArrayList<>();
                int size = DFS(userId, visited, userIds);
                if (size > maxSize) {
                    maxSize = size;
                    largestCommunityIds.clear();
                    largestCommunityIds.addAll(userIds);
                }
            }
        }
        return largestCommunityIds;

    }
}
