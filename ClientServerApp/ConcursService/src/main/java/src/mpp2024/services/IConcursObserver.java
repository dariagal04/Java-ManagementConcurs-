package src.mpp2024.services;

import src.mpp2024.domain.Participant;
import src.mpp2024.domain.PersoanaOficiu;

public interface IConcursObserver {
    void participantAdded(Participant participant) throws ConcursException;
    void oficiuLoggedIn(PersoanaOficiu referee) throws ConcursException;
    void oficiuLoggedOut(PersoanaOficiu referee) throws ConcursException;
}
