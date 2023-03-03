import React, {Component} from "react";
import {styled} from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, {tableCellClasses} from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

const StyledTableCell = styled(TableCell)(({theme}) => ({
    [`&.${tableCellClasses.head}`]: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    [`&.${tableCellClasses.body}`]: {
        fontSize: 14,
    },
}));

const StyledTableRow = styled(TableRow)(({theme}) => ({
    '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
    },
    '&:last-child td, &:last-child th': {
        border: 0,
    },
}));

class CatColorsInfo extends Component {
    state = {
        colorsCount: []
    }

    async componentDidMount() {
        const response = await fetch('/cat-info/v1/cats-statistic/cat-colors', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        });
        const body = await response.json();
        this.setState({colorsCount: body});
    }

    render() {
        const {colorsCount} = this.state;

        return (
            <TableContainer component={Paper}>
                <Table>
                    <TableHead>
                        <TableRow>
                            <StyledTableCell>Cat Color</StyledTableCell>
                            <StyledTableCell align="right">Count</StyledTableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {colorsCount.map((row) => (
                            <StyledTableRow key={row.catColor}>
                                <StyledTableCell component="th" scope="row">{row.catColor}</StyledTableCell>
                                <StyledTableCell align="right">{row.count}</StyledTableCell>
                            </StyledTableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        );
    }
}

export default CatColorsInfo;