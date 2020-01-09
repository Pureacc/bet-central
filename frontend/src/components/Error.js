import React, {Component} from 'react';
import {SnackbarContentWrapper} from "./SnackbarContentWrapper";
import Snackbar from "@material-ui/core/Snackbar";
import {connect} from "react-redux";
import {clearError} from "../actions/error";

class Error extends Component {
    constructor(props) {
        super(props);
        this.state = {open: false};
    }

    componentDidUpdate(prevProps) {
        const {message} = this.props;
        if (!!message && message !== prevProps.message) {
            this.setState({open: true});
        }
    }

    handleClose = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }

        this.setState({open: false});
        this.props.actions.clearError();
    };

    render() {
        const {message} = this.props;

        return (
            <Snackbar
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'center',
                }}
                open={this.state.open}
                autoHideDuration={6000}
                onClose={this.handleClose}
            >
                <SnackbarContentWrapper
                    onClose={this.handleClose}
                    variant="error"
                    message={message}
                />
            </Snackbar>
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