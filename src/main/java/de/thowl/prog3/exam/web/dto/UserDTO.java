package de.thowl.prog3.exam.web.dto;

/**
 * Der UserDTO (Data Transfer Object) ist eine einfache Klasse, die nur die Daten enthaelt,
 * die zwischen der Web-Schicht und der Service-Schicht uebertragen werden sollen.
 * Es enthaelt keine Geschaeftslogik oder Datenbank-Operationen.
 */
public record UserDTO(long id, String username, String email) {
};
