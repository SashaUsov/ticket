@GenericGenerator(
        name = "seq_t_com_id",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "seq_t_com_id", value = "sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
        }
)

@GenericGenerator(
        name = "seq_ticket_id",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
                @Parameter(name = "seq_ticket_id", value = "sequence"),
                @Parameter(name = "initial_value", value = "1"),
                @Parameter(name = "increment_size", value = "1"),
        }
)
package com.testproject.ticket.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
