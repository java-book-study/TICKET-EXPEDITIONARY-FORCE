package com.ticket.captain.account;

public enum Role {
    UNAUTH {
        @Override
        public String toString() {
            return "UNAUTH";
        }
    },

    ROLE_ADMIN {
        @Override
        public String toString() {
            return "ROLE_ADMIN";
        }
    },
    ROLE_USER {
        @Override
        public String toString() {
            return "ROLE_USER";
        }
    },
    ROLE_MANAGER {
        @Override
        public String toString() {
            return "ROLE_MANAGER";
        }
    };
}
