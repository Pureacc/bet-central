import React, {Component} from 'react';
import {connect} from "react-redux";
import {clearError} from "../actions/error";
import Toast from "./Toast";

class Error extends Component {
    handleClose = () => {
        this.props.actions.clearError();
    };

    render() {
        const {message} = this.props;

        return (
            <Toast variant="error" message={message} onClose={this.handleClose}/>
        );
    }
}

const mapStateToProps = state => {
    const {error} = state;
    return {
        message: error ? error.message : ""
    }
};

const mapDispatchToProps = dispatch => {
    return {
        actions: {
            clearError: () => dispatch(clearError())
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Error);