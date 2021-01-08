package com.daki.main.objects;

import java.util.ArrayList;
import java.util.List;

public class Event {

    Boolean running;
    List<Participant> participants;

    public Event() {

        running = false;
        participants = new ArrayList<>();


    }

    public Boolean getRunning() {
        return running;
    }

    public void setRunning(Boolean running) {
        this.running = running;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant);
    }

    public void removeParticipant(Participant participant) {
        this.participants.remove(participant);
    }

    public Participant getParticipantFromPlayerName(String playerName) {
        for (Participant participant : participants) {
            if (participant.getPlayer().getName().equals(playerName)) {
                return participant;
            }
        }
        return null;
    }

    public void clearParticipants() {
        participants.clear();
    }

}
