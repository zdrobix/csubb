package service;

import domain.Prietenie;
import domain.Tuple;
import domain.validators.PrietenieValidator;
import domain.validators.ValidationException;
import repo.file.UtilizatorFile;
import repo.file.PrietenieFile;
import domain.Utilizator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private final UtilizatorFile repo;
    private final PrietenieFile repoPrieteni;

    public Service(UtilizatorFile repo_, PrietenieFile repoPrieteni_) {
        this.repo = repo_;
        this.repoPrieteni = repoPrieteni_;
        this.loadFriendsFromBothRepos();
    }

    public void addUtilizator (String firstName, String lastName, long id) {
        try {
            var user = new Utilizator(firstName, lastName);
            user.setId(id);
            this.repo.save(user);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public void deleteUtilizator (long id) {
        if (this.repo.findOne(id) == null) {
            throw new ValidationException("User not found");
        }
        try {
            ArrayList<Tuple<Long, Long>> toDelete = new ArrayList<>();
            for (var prietenie : this.repoPrieteni.findAll())
            {
                if (prietenie.getIdFriend1() == id || prietenie.getIdFriend2() == id)
                    toDelete.add(prietenie.getId());
            }
            for (var delete : toDelete)
                this.repoPrieteni.delete(delete);
            for (Long idUser : this.repo.findOne(id).getFriends()) {
                this.repo.findOne(idUser).removeFriend(this.repo.findOne(id));
            }

            this.repo.delete(id);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public void addPrietenie (long id1, long id2)
    {
        try {
            var friends = new Prietenie(id1, id2, LocalDate.now());
            friends.setId(
                    new Tuple<>(
                            id1, id2));
            PrietenieValidator.validate2(friends, this.repoPrieteni, this.repo);
            this.repoPrieteni.save(friends);
            Prietenie.friendEach(friends, this.repo);
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public void deletePrietenie (long id1, long id2) {
        try {
            PrietenieValidator.validate3(id1, id2, this.repo);
            var user1 = this.repo.findOne(id1);
            var user2 = this.repo.findOne(id2);
            if (user1 == null || user2 == null)
                throw new ValidationException("User not found");
            user1.removeFriend(user2);
            user2.removeFriend(user1);
            this.repoPrieteni.delete(new Tuple<>(
                    id1, id2));
        } catch (ValidationException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    private void loadFriendsFromBothRepos() {
        for (var prietenie : this.repoPrieteni.findAll()) {
            Prietenie.friendEach(prietenie, this.repo);
        }
    }

    private Integer DFS(Long userId, boolean[] visited, List<Long> userIds) {
        visited[userId.intValue()] = true;
        int size = 1;
        userIds.add(userId);
        for (Long friendId : this.repo.findOne(userId).getFriends()) {
            if (!visited[friendId.intValue()]) {
                size += DFS(friendId, visited, userIds);
            }
        }
        return size;
    }

    public Integer numberOfCommunities() {
        boolean[] visited = new boolean[this.repo.size() + 1];
        int count = 0;
        List<Long> list = new ArrayList<>();
        for (var user : this.repo.findAll()) {
            Long userId = user.getId();
            if (!visited[userId.intValue()]) {
                DFS(userId, visited, list);
                count++;
            }
        }
        return count;
    }

    public List<Long> largestCommunity () {
        boolean[] visited = new boolean[this.repo.size() + 1];
        List<Long> largestCommunityIds = new ArrayList<>();
        int maxSize = 0;
        for (var user : this.repo.findAll()) {
            Long userId = user.getId();
            if (!visited[userId.intValue()]) {
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

    public Utilizator getUtilizator(Long id) {
        return this.repo.findOne(id);
    }
}
