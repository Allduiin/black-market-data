import Link from 'next/link';
import { useRouter } from 'next/router';

const cities = [
    { name: 'All', endpoint: 'all' },
    { name: 'Thetford', endpoint: 'Thetford' },
    { name: 'Fort Sterling', endpoint: 'Fort Sterling' },
    { name: 'Lymhurst', endpoint: 'Lymhurst' },
    { name: 'Bridgewatch', endpoint: 'Bridgewatch' },
    { name: 'Martlok', endpoint: 'Martlok' },
];

const Sidebar = ({ currentCity }) => {

    return (
        <div className="sidebar">
            <h2>City</h2>
            <ul>
                {cities.map(city => (
                    <li key={city.name}>
                        <Link href={`/?city=${city.endpoint}`} legacyBehavior>
                            <a className={currentCity === city.endpoint ? 'active' : ''}>{city.name}</a>
                        </Link>
                    </li>
                ))}
            </ul>
            <style jsx>{`
                .sidebar {
                    padding: 20px;
                    width: 200px;
                    background: #f4f4f4;
                    height: 100vh;
                }
                ul {
                    list-style: none;
                    padding: 0;
                }
                li {
                    margin: 10px 0;
                }
                a {
                    text-decoration: none;
                    color: #333;
                }
                .active {
                    font-weight: bold;
                    color: blue;
                }
            `}</style>
        </div>
    );
};

export default Sidebar;
