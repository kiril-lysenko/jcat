import React, {Component} from "react";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableRow from '@mui/material/TableRow';

class CatsStatisticInfo extends Component {

    state = {
        statInfo: {}
    }

    async componentDidMount() {
        const response = await fetch('/cat-info/v1/cats-statistic/tail-and-whiskers-length', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        const body = await response.json();
        this.setState({statInfo: body});
    }

    render() {
        const {statInfo} = this.state;

        return (
            <TableContainer>
                <Table sx={{minWidth: 500}}>
                    <TableBody>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Tail Length Mean
                            </TableCell>
                            <TableCell align="right">
                                {statInfo.tailLengthMean}
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Tail Length Median
                            </TableCell>
                            <TableCell align="right">
                                {statInfo.tailLengthMedian}
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Tail Length Mode
                            </TableCell>
                            <TableCell align="right">
                                {JSON.stringify(statInfo.tailLengthMode)}
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Whiskers Length Mean
                            </TableCell>
                            <TableCell align="right">
                                {statInfo.whiskersLengthMean}
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Whiskers Length Median
                            </TableCell>
                            <TableCell align="right">
                                {statInfo.whiskersLengthMedian}
                            </TableCell>
                        </TableRow>
                        <TableRow>
                            <TableCell component="th" scope="row">
                                Whiskers Length Mode
                            </TableCell>
                            <TableCell align="right">
                                {JSON.stringify(statInfo.whiskersLengthMode)}
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </TableContainer>
        );
    }
}

export default CatsStatisticInfo;