import React, {Component} from 'react';
import {connect} from "react-redux";
import Toast from "./Toast";
import {clearSuccess} from "../actions/success";

class Success extends Component {
    handleClose = () => {
        this.props.actions.clearSuccess();
    };

    render() {
        const {message} = this.props;

        return (
            <Toast variant="success" message={message} onClose={this.handleClose}/>
        );
    }
}

const mapStateToProps = state => {
    const {success} = state;
    return {
        message: success ? success.message : ""
    }
};

const mapDispatchToProps = dispatch => {
    return {
        actions: {
            clearSuccess: () => dispatch(clearSuccess())
        }
    }
};

export default connect(mapStateToProps, mapDispatchToProps)(Success);