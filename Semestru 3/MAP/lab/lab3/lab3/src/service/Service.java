package service;

import domain.Prietenie;
import domain.Tuple;
import domain.validators.PrietenieValidator;
import domain.validators.ValidationException;
import domain.Utilizator;

import repo.db.UserDatabaseRepository;
import repo.db.FriendshipDatabaseRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private final UserDatabaseRepository repo;
    private final FriendshipDatabaseRepository repoPrieteni;

    public Service(UserDatabaseRepository repo_, FriendshipDatabaseRepository repoPrieteni_) {
        this.repo = repo_;
        this.repoPrieteni = repoPrieteni_;
    }

    public void addUtilizator (String firstName, String lastName, long id) {
        try {
            var user = new Utilizator(firstName, lastName);
            user.setId(id);
            this.repo.save(user);
        } catch (ValidationException | SQLException e) {
            throw new ValidationException(e.getMessage());
        }
    }

    public void deleteUtilizator (long id) throws SQLException {
        if (this.repo.findOne(id) == null) {
            throw new ValidationException("User not found");
        }
        try {
            ArrayList<Tuple<Long, Long>> toDelete = new ArrayList<>();
            for (var prietenie : this.repoPrieteni.findAll())
            {
                if (prietenie.getIdFriend1() == id || prietenie.getIdFriend2() == id) {
                    this.deletePrietenie(prietenie.getIdFriend1(), prietenie.getIdFriend2());
                    System.out.println(prietenie.toString());
                }

            }
            for (Long idUser : this.repo.findOne(id).get().getFriends()) {
                this.repo.findOne(idUser).get().removeFriend(this.repo.findOne(id).get());
            }

            this.repo.delete(id);
        } catch (ValidationException | IOException e) {
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
            PrietenieValidator.validate2(friends, this.repo, this.repoPrieteni);
            this.repoPrieteni.save(friends);
        } catch (ValidationException e) {
            throw new ValidationException("1." + e.getMessage());
        } catch (SQLException e) {
            throw new ValidationException("2." + e.getMessage());
        } catch (IOException e) {
            throw new ValidationException("3." + e.getMessage());
        }
    }

    public void deletePrietenie (long id1, long id2) {
        try {
            PrietenieValidator.validate3(id1, id2, this.repo);
            var user1 = this.repo.findOne(id1).get();
            var user2 = this.repo.findOne(id2).get();
            if (user1 == null || user2 == null)
                throw new ValidationException("User not found");
            user1.removeFriend(user2);
            user2.removeFriend(user1);
            this.repoPrieteni.delete(new Tuple<>(
                    id1, id2));
        } catch (ValidationException e) {
            throw new ValidationException("1." + e.getMessage());
        } catch (SQLException e) {
            throw new ValidationException("2." + e.getMessage());
        } catch (IOException e) {
            throw new ValidationException("3." + e.getMessage());
        }
    }

    private Integer DFS(Long userId, boolean[] visited, List<Long> userIds) throws SQLException {
        try {
            visited[userId.intValue()] = true;
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
                if (!visited[friendId.intValue()]) {
                    size += DFS(friendId, visited, userIds);
                }

            return size;
        }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public Integer numberOfCommunities() throws SQLException {
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

    public List<Long> largestCommunity () throws SQLException {
        try {
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
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public Utilizator getUtilizator(Long id) throws SQLException {
        return this.repo.findOne(id).get();
    }
}
