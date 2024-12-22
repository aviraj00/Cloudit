package com.scm.smart_contact_manager.helper;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message
{

    private String content;
    @Builder.Default
    private MessageType type=MessageType.blue;
}
