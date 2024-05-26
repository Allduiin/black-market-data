import { useState } from 'react';

const backgroundColor = 'rgba(47,79,79,0.84)';
const Table = ({ data }) => {
    const [selectedRow, setSelectedRow] = useState(null);

    const getLighterColorForCity = (city) => {
        switch (city) {
            case 'Thetford':
                return 'rgba(216,191,216,0.68)'; // Светло-фиолетовый
            case 'Fort Sterling':
                return '#E0FFFF'; // Светло-голубой
            case 'Lymhurst':
                return '#90EE90'; // Светло-зеленый
            case 'Bridgewatch':
                return '#FFDAB9'; // Светло-оранжевый
            case 'Martlok':
                return '#BDECB6'; // Светло-салатовый, менее яркий
            default:
                return '#F0F0F0'; // Светло-серый по умолчанию
        }
    };

    const handleRowClick = (index) => {
        setSelectedRow(index);
    };

    return (
        <div className="table-container">
            <table>
                <thead>
                <tr>
                    <th>Category</th>
                    <th>Russian Name</th>
                    <th colSpan="2">4.0</th>
                    <th colSpan="2">4.1</th>
                    <th colSpan="2">5.0</th>
                    <th colSpan="2">5.1</th>
                    <th colSpan="2">6.0</th>
                    <th colSpan="2">6.1</th>
                </tr>
                </thead>
                <tbody>
                {data.map((item, index) => {
                    const lighterColors = getLighterColorForCity(item.city);
                    const priceBackground = lighterColors;
                    const rowStyle = selectedRow === index ? { border: '2px solid #000', backgroundColor: 'rgba(0, 0, 0, 0.1)' } : {};

                    return (
                        <tr key={index} onClick={() => handleRowClick(index)} style={rowStyle}>
                            <td>{item.category}</td>
                            <td>{item.russianName}</td>
                            <td style={{ backgroundColor: `${priceBackground}` }}>{item.prPrice4_0}</td>
                            <td style={{ backgroundColor: backgroundColor, color: 'white' }}>{item.bmPrice4_0}</td>
                            <td style={{ backgroundColor: `${priceBackground}` }}>{item.prPrice4_1}</td>
                            <td style={{ backgroundColor: backgroundColor, color: 'white' }}>{item.bmPrice4_1}</td>
                            <td style={{ backgroundColor: `${priceBackground}` }}>{item.prPrice5_0}</td>
                            <td style={{ backgroundColor: backgroundColor, color: 'white' }}>{item.bmPrice5_0}</td>
                            <td style={{ backgroundColor: `${priceBackground}` }}>{item.prPrice5_1}</td>
                            <td style={{ backgroundColor: backgroundColor, color: 'white' }}>{item.bmPrice5_1}</td>
                            <td style={{ backgroundColor: `${priceBackground}` }}>{item.prPrice6_0}</td>
                            <td style={{ backgroundColor: backgroundColor, color: 'white' }}>{item.bmPrice6_0}</td>
                            <td style={{ backgroundColor: `${priceBackground}` }}>{item.prPrice6_1}</td>
                            <td style={{ backgroundColor: backgroundColor, color: 'white' }}>{item.bmPrice6_1}</td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
            <style>{`
                .table-container {
                    margin: 20px;
                    padding: 20px;
                    background: #fff;
                    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                }
                table {
                    width: 100%;
                    border-collapse: collapse;
                }
                th, td {
                    border: 1px solid #ddd;
                    padding: 8px;
                }
                th {
                    background: #f4f4f4;
                }
                tr:hover {
                    cursor: pointer;
                }
            `}</style>
        </div>
    );
};

export default Table;
