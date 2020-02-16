import React, {Component} from 'react';
import {connect} from "react-redux";
import Toast from "./Toast";
import {clear} from "../actions/message";

class Message extends Component {
    handleClose = () => {
        this.props.actions.clear();
    };

    render() {
        const {message, variant} = this.props;

        return (
            <Toast variant={variant} message={message} onClose={this.handleClose}/>
        );
    }
}

const mapStateToProps = (state) => {
    const {message} = state;
    return {
        message: message.message,
        variant: message.variant
    }
};

const mapDispatchToProps = dispatch => {
    return {
        actions: {
            clear: () => dispatch(clear())
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Message);