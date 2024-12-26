package com.scm.smart_contact_manager.helper;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String content;

    @Builder.Default
    private MessageType type = MessageType.blue; // Updated to proper enum constant (assuming BLUE is in the MessageType enum)

    // Optional: Add convenience constructors if needed
    public Message(String content) {
        this.content = content;
        this.type = MessageType.blue; // Default type
    }
}