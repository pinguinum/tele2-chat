package org.tele2chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.tele2chat.model.Message;

@Repository
public interface MessagesRepository extends JpaRepository<Message, Long> {
    Message save(Message message);

    void deleteById(Long aLong);
}
